package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.configurations.Constants;
import edu.birzeit.swms.dtos.UserDto;
import edu.birzeit.swms.enums.UserRole;
import edu.birzeit.swms.exceptions.DatabaseException;
import edu.birzeit.swms.mappers.UserMapper;
import edu.birzeit.swms.models.Citizen;
import edu.birzeit.swms.models.ConfirmationToken;
import edu.birzeit.swms.models.SMS;
import edu.birzeit.swms.models.User;
import edu.birzeit.swms.repositories.CitizenRepository;
import edu.birzeit.swms.repositories.ConfirmationTokenRepository;
import edu.birzeit.swms.repositories.UserRepository;
import edu.birzeit.swms.services.ConfirmationTokenService;
import edu.birzeit.swms.services.EmailSenderService;
import edu.birzeit.swms.services.SMSService;
import edu.birzeit.swms.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CitizenRepository citizenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    SMSService smsService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    User admin;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            log.info("loadUserByUsername --- loading user username to authenticate");
            if (username.equals(Constants.ADMIN_USENAME)) {
                log.warn("loadUserByUsername --- trying to login as an administrator");
                return admin;
            } else {
                User user = userRepository.findByUsername(username).orElseThrow(() -> {
                    log.error(String.format("loadUserByUsername --- user with username: '%s' not found", username));
                    return new UsernameNotFoundException(String.format("User with username: '%s' not found", username));
                });
                log.debug(String.format("loadUserByUsername --- user was found: '%s'", user));
                return user;
            }
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(String.format("loadUserByUsername --- database persisting error accessing user table\n%s", ex));
            throw new DatabaseException(ex);
        }
    }

    public void signUpUser(UserDto userDto) {

        User user = userMapper.dtoToUser(userDto);

        final String encryptedPassword = passwordEncoder.encode(user.getPassword());

        Citizen citizen = new Citizen();
        citizen.setUsername(user.getUsername());
        citizen.setFirstName(user.getFirstName());
        citizen.setLastName(user.getLastName());
        citizen.setAddress(user.getAddress());
        citizen.setPhone(user.getPhone());
        citizen.setEnabled(false);
        citizen.setRole(UserRole.CITIZEN);
        citizen.setPassword(encryptedPassword);
        final Citizen createdUser = citizenRepository.save(citizen);

        final ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
//        sendConfirmationMail(createdUser.getEmail(), confirmationToken.getConfirmationToken());
        sendConfirmationMail(createdUser.getPhone(), confirmationToken.getConfirmationToken());
    }

    public void sendConfirmationMail(String userMail, String token) {

//        final SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(userMail);
//        mailMessage.setSubject("Mail Confirmation Link!");
//        mailMessage.setFrom("layth@assert.ps");
//        mailMessage.setText(
//                "Thank you for registering in SWMS. Please click on the below link to activate your account." + "http://swms.ga/api/v1/sign-up/confirm?token="
//                        + token);
//
//        emailSenderService.sendEmail(mailMessage);

        final SMS sms = new SMS();
        sms.setTo(userMail);
        sms.setMessage("Thank you for registering in SWMS. Please click on the below link to activate your account." + "http://swms.ga/api/v1/confirm?token="
                + token);
        smsService.send(sms);
    }

    public void confirmUser(String token) {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findAllByConfirmationToken(token).orElseThrow(() ->
                new IllegalArgumentException("token not valid")
        );
        final User user = confirmationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());

    }

    public UserDto getUser(){
        String username;
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = principal.toString();
        } catch (Exception e) {
            throw new IllegalStateException("you are not logged in user");
        }
        User user=userRepository.findByUsername(username).orElseThrow(() ->
            new IllegalStateException("User Not Found")
        );
        UserDto userDto= userMapper.userToDto(user);
        return userDto;
    }


}
