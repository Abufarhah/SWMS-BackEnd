#Libraries
import RPi.GPIO as GPIO
import time
import requests
import json
 
#GPIO Mode (BOARD / BCM)
GPIO.setmode(GPIO.BCM)
 
#set GPIO Pins
GPIO_TRIGGER = 4
GPIO_ECHO = 17
 
#set GPIO direction (IN / OUT)
GPIO.setup(GPIO_TRIGGER, GPIO.OUT)
GPIO.setup(GPIO_ECHO, GPIO.IN)
 
def distance():
    # set Trigger to HIGH
    GPIO.output(GPIO_TRIGGER, True)
 
    # set Trigger after 0.01ms to LOW
    time.sleep(0.00001)
    GPIO.output(GPIO_TRIGGER, False)
 
    StartTime = time.time()
    StopTime = time.time()
 
    # save StartTime
    while GPIO.input(GPIO_ECHO) == 0:
        StartTime = time.time()
 
    # save time of arrival
    while GPIO.input(GPIO_ECHO) == 1:
        StopTime = time.time()
 
    # time difference between start and arrival
    TimeElapsed = StopTime - StartTime
    # multiply with the sonic speed (34300 cm/s)
    # and divide by 2, because there and back
    distance = (TimeElapsed * 34300) / 2
 
    return distance
 
if __name__ == '__main__':
    try:
        print ("started")
        headers = {'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2MDU1NTk0NjIsImV4cCI6MTYxNDExNzYwMH0.bKZoR5-4qVmQ0V5nyTLxGxdXjHLP6CCfvvAntB8EvO0'}
        bin = {"location": {
                                "x": 31.968146,
                                "y": 35.193396
                                   },
                                   "status": "UNDER_THRESHOLD"}
        response = requests.post("http://swms.ga/api/v1/bins",json=bin,headers=headers)
        retrievedBin = json.loads(response.text)
        id = retrievedBin['id']
        distMax = distance()
        statusBefore = "UNDER_THRESHOUD"
        lastTime = 0
        while True:
            threshold1 = distMax / 3
            threshold2 = 2 * threshold1
            dist = distance()
            if dist > threshold2:
                status = "UNDER_THRESHOLD"
            elif dist > threshold1:
                status = "ABOUT_TO_THRESHOLD"
            else:
                status = "OVER_THRESHOLD"
            time.sleep(1)
            if statusBefore != status:
                if status == "OVER_THRESHOLD":
                    lastTime=int(time.time())
                print ("b")
                response = requests.put("http://swms.ga/api/v1/bins?id="+str(id)+"&status="+status,status,headers=headers)
                print (response.status_code)
                statusBefore = status
            if status == "OVER_THRESHOLD":
                currentTime=int(time.time())
                print(lastTime)
                print(currentTime)
                if(currentTime - lastTime > 14400):
                    status = "EMERGENCY"
                    response = requests.put("http://swms.ga/api/v1/bins?id="+str(id)+"&status="+status,status,headers=headers)
                    response = requests.post("http://swms.ga/api/v1/bins/"+str(id),status,headers=headers)
            if dist > distMax:
                distMax=dist
            print ("Measured Distance = %.1f cm" % dist)
            time.sleep(1)
 
        # Reset by pressing CTRL + C
    except KeyboardInterrupt:
        print("Measurement stopped by User")
        GPIO.cleanup()
