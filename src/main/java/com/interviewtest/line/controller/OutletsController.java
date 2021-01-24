package com.interviewtest.line.controller;

import com.interviewtest.line.dto.OutletsDto;
import com.interviewtest.line.dto.UserProfileDto;
import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.repository.OutletsRepository;
import com.interviewtest.line.repository.UserProfileRepository;
import com.interviewtest.line.response.JsonResponse;
import com.interviewtest.line.services.OutletsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Oki-Peter
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/outlets")
public class OutletsController {
    
    @Autowired
    private OutletsService outletsService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private OutletsRepository outletsRepository;

    @Autowired
    private UserManagement userManagement;

    @RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return outletsService.greet();
    }
    
    @GetMapping(path = "/find/outlets/{outletId}")
    public ResponseEntity<Object> findOutlet(@PathVariable Long outletId){
        Outlets outlet = outletsRepository.findById(outletId).orElse(null);
        if (outlet==null){
            return ResponseEntity.ok(new JsonResponse("Outlet Record Not Found!", HttpStatus.NOT_FOUND));
        }

       OutletsDto outletsDto = new OutletsDto();
        outletsDto.setId(outlet.getId());
        outletsDto.setUsersAccountId(outlet.getUsersAccount().getId());
        outletsDto.setOutletName(outlet.getOutletName());
        outletsDto.setPhone(outlet.getPhone());
        outletsDto.setEmail(outlet.getEmail());
        outletsDto.setStreetAddress(outlet.getStreetAddress());

        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", outletsDto));
    }


    @GetMapping(path = "/find/all")
    public ResponseEntity<Object> findAllOutlets(){
        List<Outlets> outlets = outletsRepository.findAllByDeletedOrderById(false);
        if (outlets==null){
            return ResponseEntity.ok(new JsonResponse("Outlet Record Not Found!", HttpStatus.NOT_FOUND));
        }

        List<OutletsDto> outletsDtos = new ArrayList<>();
        outlets.stream().map(outlet -> {
            OutletsDto outletsDto = new OutletsDto();
            outletsDto.setId(outlet.getId());
            outletsDto.setUsersAccountId(outlet.getUsersAccount().getId());
            outletsDto.setOutletName(outlet.getOutletName());
            outletsDto.setPhone(outlet.getPhone());
            outletsDto.setEmail(outlet.getEmail());
            outletsDto.setStreetAddress(outlet.getStreetAddress());
            return outletsDto;
        }).forEachOrdered(outletsDto -> {
            outletsDtos.add(outletsDto);
        });
        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", outletsDtos));
    }
    
    
    @Transactional
    @PostMapping(path = "/save/outlet")
    public ResponseEntity<Object> saveOutlet(@RequestBody OutletsDto outletsDto) {

        Outlets outlet = outletsService.persistOutlets(outletsDto);
        outletsDto.setUsersAccountId(outlet.getId());
        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", outletsDto));
    }

    @Transactional
    @PostMapping(path = "/create-user-profile-for-outlet")
    public ResponseEntity<Object> createUserProfileForOutlet(@RequestBody UserProfileDto userProfileDto){
        UsersAccount user = userManagement.findByUsername(userProfileDto.getEmail());
        if (user == null){
            return ResponseEntity.ok(new JsonResponse("Outlet User Account Not Found!", HttpStatus.NOT_FOUND));
        }
        UserProfile userProfile = outletsService.persistOutletsUserProfile(userProfileDto);
        userProfileDto.setId(userProfile.getId());
        return ResponseEntity.ok(new JsonResponse("Outlet User Created Successfully!", userProfileDto));

    }

    
//    @GetMapping(path = "/find/users")
//    public ResponseEntity<Object> findOutletsUserList(
//            HttpServletRequest request,
//            HttpServletResponse response) {
//        List<UserProfileDto> userList = outletsService.findCurrentOutletsUserProfileDto();
//        return ResponseEntity.ok(new JsonResponse("See Data Object for Details!", userList));
//    }

//    @Transactional
//    @PostMapping(path = "/save/users")
//    public ResponseEntity<Object> saveOutletsUser(@RequestBody UserProfileDto userProfileDto,
//                                                   HttpServletRequest request,
//                                                   HttpServletResponse response) {
//        UserProfile userProfile = outletsService.persistOutletsUser(userProfileDto);
//        userProfileDto.setId(userProfile.getId());
//        return ResponseEntity.ok(new JsonResponse("Outlets User Created Successfully!", userProfileDto));
//    }
}
