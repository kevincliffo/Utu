package com.utu.utu;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static android.os.UserHandle.readFromParcel;

/**
 * Created by kevin on 26/09/2017.
 */

public class UserDetailsObject implements Serializable {

    private int nxrwUserId = 0;
    private String szxrwLocation = "";
    private String szxrwServiceProviding = "";
    private String szxrwFullName = "";
    private String szxrwEmail = "";
    private String szxrwPassword = "";
    private String szxrwIDNumber = "";
    private String szxrwMobileNumber = "";
    private String szxrwAddress = "";
    private Bitmap bmxrwImage = null;
    private String szxrwProfileImageName = "";
    private String szxrwProfileImageURL = "";
    private String szxrwDateOfBirth = "";
    private int nxrwUserType = 0;
    private int nxrwUserActivated = 0;
    private String szxrwToken = "";

    public UserDetailsObject()
    {
        this.szxrwEmail = szxrwEmail;
        this.szxrwFullName = szxrwFullName;
    }

    public UserDetailsObject(Parcel in) {
        readFromParcel(in);
    }

    public void setUserId(int nvUserId)
    {
        this.nxrwUserId = nvUserId;
    }
    public int getUserId()
    {
        return nxrwUserId;
    }

    public void setLocation(String szvLocation)
    {
        this.szxrwLocation = szvLocation;
    }
    public String getLocation()
    {
        return szxrwLocation;
    }

    public void setServiceProviding(String szvServiceProviding)
    {
        this.szxrwServiceProviding = szvServiceProviding;
    }
    public String getServiceProviding()
    {
        return szxrwServiceProviding;
    }

    public void setFullName(String szvFullName)
    {
        this.szxrwFullName = szvFullName;
    }
    public String getFullName()
    {
        return szxrwFullName;
    }

    public void setEmail(String szvEmail)
    {
        this.szxrwEmail = szvEmail;
    }
    public String getEmail()
    {
        return szxrwEmail;
    }

    public void setPassword(String szvPassword)
    {
        this.szxrwPassword = szvPassword;
    }
    public String getPassword()
    {
        return szxrwPassword;
    }

    public void setIDNumber(String szvIDNumber)
    {
        this.szxrwIDNumber = szvIDNumber;
    }
    public String getIDNumber()
    {
        return szxrwIDNumber;
    }

    public void setMobileNumber(String szvMobileNumber)
    {
        this.szxrwMobileNumber = szvMobileNumber;
    }
    public String getMobileNumber()
    {
        return szxrwMobileNumber;
    }

    public void setAddress(String szvAddress)
    {
        this.szxrwAddress = szvAddress;
    }
    public String getAddress()
    {
        return szxrwAddress;
    }

    public void setImage(Bitmap bmvImage)
    {
        this.bmxrwImage = bmvImage;
    }
    public Bitmap getImage()
    {
        return bmxrwImage;
    }

    public void setProfileImageName(String szvProfileImageName)
    {
        this.szxrwProfileImageName = szvProfileImageName;
    }
    public String getProfileImageName()
    {
        return szxrwProfileImageName;
    }

    public void setProfileImageURL(String szvProfileImageURL)
    {
        this.szxrwProfileImageURL = szvProfileImageURL;
    }
    public String getProfileImageURL()
    {
        return szxrwProfileImageURL;
    }

    public void setDateOfBirth(String szvDateOfBirth)
    {
        this.szxrwDateOfBirth = szvDateOfBirth;
    }
    public String getDateOfBirth()
    {
        return szxrwDateOfBirth;
    }

    public void setUserType(int nvUserType)
    {
        this.nxrwUserType = nvUserType;
    }
    public int getUserType()
    {
        return nxrwUserType;
    }

    public void setUserActivated(int nvUserActivated)
    {
        this.nxrwUserActivated = nvUserActivated;
    }
    public int getUserActivated()
    {
        return nxrwUserActivated;
    }

    public void setToken(String szvToken)
    {
        this.szxrwToken = szvToken;
    }
    public String getToken()
    {
        return szxrwToken;
    }

}
