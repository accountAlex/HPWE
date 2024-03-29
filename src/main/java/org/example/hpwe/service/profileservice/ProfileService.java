package org.example.hpwe.service.profileservice;

import org.example.hpwe.entity.User;
import org.example.hpwe.forms.profile.AccountChangeForm;
import org.example.hpwe.forms.profile.PasswordChangeForm;
import org.example.hpwe.forms.profile.PersonalChangeForm;
import org.example.hpwe.models.UserToProfileModel;

public interface ProfileService {
    UserToProfileModel getUserProfile(User user);
    User updatePersonalInfo(PersonalChangeForm form);
    User updateSystemInfo(AccountChangeForm form);
    User updatePassword(PasswordChangeForm form);
}
