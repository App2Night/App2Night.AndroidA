package de.dhbw.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

import de.dhbw.app2night.MainActivity;
import de.dhbw.utils.PropertyUtil;

/**
 * Created by Tobias Berner on 02.11.2016.
 */

    public class User {

        private String HostId;
        private String UserName;

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String Username) {
            this.UserName = Username;
        }


        public String getHostId() {
            return HostId;
        }

        public void setHostId(String UserId) {
            this.HostId = UserId;
        }
    }
