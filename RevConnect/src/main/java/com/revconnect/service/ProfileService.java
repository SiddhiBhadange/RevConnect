package com.revconnect.service;

import com.revconnect.dao.ProfileDAO;

import com.revconnect.model.Profile;

import java.util.List;

public class ProfileService {
    ProfileDAO dao = new ProfileDAO();

        public Profile getProfileByUserId(int userId) {

            return dao.getProfileByUserId(userId);
        }

        public boolean updateProfile(Profile profile) {
            // If profile exists → update
            if (dao.getProfileByUserId(profile.getUserId()) != null) {
                return dao.updateProfile(profile);
            }
            // Else → insert
            return dao.saveOrInsert(profile);
        }

        public List<Profile> searchProfiles(String keyword) {

            return dao.searchProfiles(keyword);
        }
    }



