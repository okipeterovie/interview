package com.interviewtest.line.controller;

import com.interviewtest.line.dto.UserDto;
import com.interviewtest.line.dto.UserProfileDto;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.repository.UserProfileRepository;
import com.interviewtest.line.response.JsonResponse;
import com.interviewtest.line.services.OutletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oki-PEter
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private OutletsService outletsService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    
//    @GetMapping(path = "/outlets/users")
//    public ResponseEntity<Object> findOutletsUserList(@RequestBody UserDto userDto,
//            HttpServletRequest request,
//            HttpServletResponse response) {
//        List<UserProfileDto> userList = outletsService.findCurrentOutletsUserProfileDto();
//        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", userList));
//    }

//    @GetMapping(path = "/outlets/users/{userProfileEmail}")
//    public ResponseEntity<Object> findOutletsUserEmail(@PathVariable String userProfileEmail) {
//        List<UserProfile> userProfiles = userProfileRepository.findUserProfileByEmail(userProfileEmail);
//        if (userProfiles == null) {
//            return ResponseEntity.ok(new JsonResponse("Outlet User Record Not Found!", HttpStatus.NOT_FOUND));
//        }
//        List<UserProfileDto> userProfileDtos = new ArrayList<>();
//        userProfiles.stream().map(userProfile -> {
//            UserProfileDto userProfileDto = new UserProfileDto();
//            userProfileDto.setId(userProfile.getId());
//            userProfileDto.setUserType(userProfile.getUserType());
//            userProfileDto.setPosition(userProfile.getPosition());
//            userProfileDto.setFirstName(userProfile.getFirstName());
//            userProfileDto.setLastName(userProfile.getLastName());
//            userProfileDto.setEmail(userProfile.getEmail());
//            userProfileDto.setPhone(userProfile.getPhone());
//            userProfileDto.setCountry(userProfile.getCountry());
//            userProfileDto.setStreetAddress(userProfile.getStreetAddress());
//            userProfileDto.setUsersAccountId(userProfile.getUsersAccount().getId());
//            userProfileDto.setTimeCreated(userProfile.getTimeCreated());
//            userProfileDto.setTimeUpdated(userProfile.getTimeUpdated());
//
//            return userProfileDto;
//        }).forEachOrdered(userProfileDto -> {
//            userProfileDtos.add(userProfileDto);
//        });
//        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", userProfileDtos));
//    }


//    @GetMapping(path = "/save/outlets/users")
//    public ResponseEntity<Object> saveOutletsUserList(@RequestBody UserProfileDto userDto,
//            HttpServletRequest request,
//            HttpServletResponse response) {
//        List<UserProfileDto> userList = outletsService.findCurrentOutletsUserProfileDto();
//        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", userList));
//    }
    
    
}
