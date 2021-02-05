package edu.birzeit.swms.services.implementations;

import edu.birzeit.swms.dtos.RoundDto;
import edu.birzeit.swms.enums.RoundStatus;
import edu.birzeit.swms.exceptions.CustomException;
import edu.birzeit.swms.exceptions.ResourceNotFoundException;
import edu.birzeit.swms.mappers.RoundMapper;
import edu.birzeit.swms.models.Bin;
import edu.birzeit.swms.models.Round;
import edu.birzeit.swms.models.User;
import edu.birzeit.swms.repositories.BinRepository;
import edu.birzeit.swms.repositories.RoundRepository;
import edu.birzeit.swms.repositories.UserRepository;
import edu.birzeit.swms.services.RoundService;
import edu.birzeit.swms.utils.SWMSUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static edu.birzeit.swms.configurations.Constants.ADMIN_USERNAME;

@Log
@Service
public class RoundServiceImpl implements RoundService {

    @Autowired
    RoundRepository roundRepository;

    @Autowired
    BinRepository binRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoundMapper roundMapper;

    @Autowired
    SWMSUtil util;

    @Autowired
    User admin;

    @Override
    public List<RoundDto> getRounds() {
        List<RoundDto> roundDtoList = new ArrayList<>();
        roundRepository.findAll().forEach(round -> roundDtoList.add(roundMapper.roundToDto(round)));
        User user = util.getLoggedInUser();
        if (!user.getUsername().equals(ADMIN_USERNAME)) {
            return roundDtoList.stream().filter(roundDto ->
                    roundDto.getUserId() == user.getId()).collect(Collectors.toList());
        }
        return roundDtoList;
    }

    @Override
    public RoundDto getRound(int id) {
        Round round = roundRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Round", "id", id));
        return roundMapper.roundToDto(round);
    }

    @Override
    public RoundDto addRound(RoundDto roundDto) {
        Round round = roundMapper.dtoToRound(roundDto);
        round.setRoundStatus(RoundStatus.IN_PROGRESS);
        User user = util.getLoggedInUser();
        round.setEmployee(user);
        List<Bin> binList = new ArrayList<>();
        binRepository.findAllById(roundDto.getBinIdsList()).forEach(bin -> binList.add(bin));
        round.setBinList(binList);
        Round savedRound = roundRepository.save(round);
        RoundDto savedRoundDto = roundMapper.roundToDto(savedRound);
        return savedRoundDto;
    }

    @Override
    public RoundDto updateRound(RoundDto roundDto, int id) {
        Round round = roundRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Round", "id", id));
        User user = util.getLoggedInUser();
        if (user.getUsername().equals(round.getEmployee().getUsername())) {
            List<Bin> binList = new ArrayList<>();
            binRepository.findAllById(roundDto.getBinIdsList()).forEach(bin -> binList.add(bin));
            round.setBinList(binList);
            round.setRoundStatus(roundDto.getRoundStatus());
            Round savedRound = roundRepository.save(round);
            RoundDto savedRoundDto = roundMapper.roundToDto(savedRound);
            return savedRoundDto;
        } else {
            throw new CustomException("you are not authorized to update this round", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public void deleteRound(int id) {
        Round round = roundRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Round", "id", id));
        User user = util.getLoggedInUser();
        if (user.getUsername().equals(round.getEmployee().getUsername()) || util.isAdmin()) {
            round.getBinList().forEach(bin -> {
                bin.getRoundList().removeIf(round1 -> round1.getId() == round.getId());
                binRepository.save(bin);
            });
            roundRepository.deleteById(id);
        } else {
            throw new CustomException("you are not authorized to update this round", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public RoundDto updateRoundStatus(RoundStatus status, int id) {
        Round round = roundRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Round", "id", id));
        round.setRoundStatus(status);
        Round savedRound = roundRepository.save(round);
        RoundDto savedRoundDto = roundMapper.roundToDto(savedRound);
        return savedRoundDto;
    }

}
