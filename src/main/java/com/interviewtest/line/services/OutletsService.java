package com.interviewtest.line.services;

import com.interviewtest.line.dto.OutletsDto;
import com.interviewtest.line.dto.UserDto;
import com.interviewtest.line.dto.UserProfileDto;
import com.interviewtest.line.entity.Outlets;
import com.interviewtest.line.entity.UserProfile;
import com.interviewtest.line.entity.UsersAccount;
import com.interviewtest.line.entity.WalletMaster;
import com.interviewtest.line.enumeration.UserType;
import com.interviewtest.line.managers.UserManagement;
import com.interviewtest.line.repository.OutletsRepository;
import com.interviewtest.line.repository.UserProfileRepository;
import com.interviewtest.line.repository.WalletMasterRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Ademola Aina
 */
@Service
@Data
public class OutletsService {

    private Logger logger = Logger.getLogger(OutletsService.class.getName());

    @Autowired
    private OutletsRepository outletsRepository;
    
    @Autowired
    private UserManagement userManagement;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private WalletMasterService walletMasterService;

    @Autowired
    private WalletMasterRepository walletMasterRepository;

    public Outlets findOutletByCurrentUser(){
        UsersAccount userAccount = userManagement.getRolePrivilegeManager().getUser();
        if (userAccount!=null){
            //Outlets admin
            Outlets outlets  = outletsRepository.findByUsersAccount(userAccount);

            //Check for general user that belongs to a firm
            if(outlets==null){
                UserProfile userProfile = userManagement.getUserProfileRepository().findByUsersAccount(userAccount);
                if (userProfile!=null){
                    outlets = outletsRepository.findFirstByOutletsUsersIn(Arrays.asList(userProfile));
                }                
            }
            return outlets;
        }
        return null;
    }
    
    /**
     * @return 
     */
    public List<UserProfile> findOutletsUsers(){
        UsersAccount userAccount = userManagement.getRolePrivilegeManager().getUser();
        List<UserProfile> userProfiles = new ArrayList<>();
        if (userAccount.isAdmin()) {
            userProfiles = userManagement.getUserProfileRepository().findAll();
        } else {
            Outlets outlets = findOutletByCurrentUser();
            if (outlets!=null){
                userProfiles = outlets.getOutletsUsers();
            }
        }
        return userProfiles;
    }
    
    public List<UserProfileDto> findCurrentOutletsUserProfileDto(){
        List<UserProfile> userProfiles = findOutletsUsers();
        if (userProfiles==null){
            return null;
        }
        return findAllOutletsUserProfileDto(userProfiles);
    }
    
    public UserProfileDto findOutletsUserProfileDto(UserProfile up){
        if (up==null){
            return null;
        }
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setId(up.getId());
        userProfileDto.setUsersAccountId(up.getUsersAccount().getId());
        userProfileDto.setUserType(up.getUserType());
        userProfileDto.setFirstName(up.getFirstName());
        userProfileDto.setLastName(up.getLastName());
        userProfileDto.setPosition(up.getPosition());
        userProfileDto.setEmail(up.getEmail());
        userProfileDto.setPhone(up.getPhone());
        userProfileDto.setStreetAddress(up.getStreetAddress());
        userProfileDto.setTimeCreated(up.getTimeCreated());
        userProfileDto.setTimeUpdated(up.getTimeUpdated());
        return userProfileDto;
    }
    
    
    public List<UserProfileDto> findAllOutletsUserProfileDto(List<UserProfile> userProfiles){
        List<UserProfileDto> userList = new ArrayList<>();
        if (userProfiles != null && !userProfiles.isEmpty()) {
            userProfiles.forEach((up) ->{
                userList.add(this.findOutletsUserProfileDto(up));
            });
        }
        return userList;
    }

    public UserProfile persistOutletsUserProfile(UserProfileDto userProfileDto){
        UsersAccount user = userManagement.findByUsername(userProfileDto.getEmail());

        UserProfile userProfile = this.getUserManagement().getUserProfileRepository().findByUsersAccount(user);
        if (userProfile==null){
            userProfile = new UserProfile();
        }

        userProfile.setUsersAccount(user);
        userProfile.setId(userProfileDto.getId());
        userProfile.setEmail(user.getEmail());
        userProfile.setPhone(userProfileDto.getPhone());
        userProfile.setPosition(userProfileDto.getPosition());
        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());
        userProfile.setStreetAddress(userProfileDto.getStreetAddress());
        userProfile.setUserType(UserType.SUB_ADMIN);
        userProfile.setCountry(userProfileDto.getCountry());


        userProfile = this.getUserManagement().getUserProfileRepository().save(userProfile);

        Outlets outlets= this.findOutletByCurrentUser();
        if (outlets!=null){
            outlets.getOutletsUsers().add(userProfile);
            outletsRepository.save(outlets);
        }

        if (outlets!=null && userProfile !=null){
            WalletMaster walletMaster = new WalletMaster();

            walletMaster.setTotalAmountAvailable(0.00);
            walletMaster.setOutlets(outlets);
            walletMaster.setUserProfile(userProfile);

            walletMasterRepository.save(walletMaster);
        }


        return this.getUserManagement().getUserProfileRepository().save(userProfile);
    }
    
    /**
     * @param userProfileDto
     * @return 
     */
    public UserProfile persistOutletsUser(UserProfileDto userProfileDto) {

        UserDto userDto = new UserDto();
        userDto.setId(userProfileDto.getUsersAccountId());
        userDto.setEmail(userProfileDto.getEmail());
        userDto.setPassword(userProfileDto.getPassword());
        userDto.setConfirmPassword(userProfileDto.getConfirmPassword());
        userDto.setUserType(UserType.OUTLET_USER);
        UsersAccount user = userManagement.saveUser(userDto);
        UserProfile userProfile = this.getUserManagement().getUserProfileRepository().findByUsersAccount(user);
        if (userProfile==null){
            userProfile = new UserProfile();
        }
        userProfile.setUsersAccount(user);
        userProfile.setEmail(userDto.getEmail());
        userProfile.setPhone(userProfileDto.getPhone());
        userProfile.setPosition(userProfileDto.getPosition());
        userProfile.setFirstName(userProfileDto.getFirstName());
        userProfile.setLastName(userProfileDto.getLastName());
        userProfile.setStreetAddress(userProfileDto.getStreetAddress());
        userProfile.setUserType(userDto.getUserType());
        userProfile.setCountry(userProfileDto.getCountry());

        userProfile = this.getUserManagement().getUserProfileRepository().save(userProfile);

        Outlets outlets = this.findOutletByCurrentUser();
        if (outlets!=null){
            outlets.getOutletsUsers().add(userProfile);
            outletsRepository.save(outlets);
        }

        if (outlets!=null && userProfile !=null){
            WalletMaster walletMaster = new WalletMaster();

            walletMaster.setTotalAmountAvailable(0.00);
            walletMaster.setOutlets(outlets);
            walletMaster.setUserProfile(userProfile);

            walletMasterRepository.save(walletMaster);
        }

        return this.getUserManagement().getUserProfileRepository().save(userProfile);
    }
    
    public Outlets persistOutlets(OutletsDto outletsDto) {
        Outlets outlets = new Outlets();
        if (outletsDto.getId() != null && outletsDto.getId() > 0) {
            outlets = outletsRepository.findById(outletsDto.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid Outlets ID!"));
        }

        UserDto userDto = new UserDto();
        userDto.setId(outletsDto.getUsersAccountId());
        userDto.setEmail(outletsDto.getEmail());
        userDto.setPassword(outletsDto.getPassword());
        userDto.setConfirmPassword(outletsDto.getConfirmPassword());
        userDto.setUserType(UserType.SUB_ADMIN);
        UsersAccount user = userManagement.saveUser(userDto);
        
        outlets.setUsersAccount(user);
        outlets.setStreetAddress(outletsDto.getStreetAddress());
        outlets.setEmail(user.getEmail());
        outlets.setOutletName(outletsDto.getOutletName());
        outlets.setPhone(outletsDto.getPhone());

        return outletsRepository.save(outlets);
    }


}
