package com.utu.utu;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kevin on 26/09/2017.
 */

public class ProviderDetailObject implements Serializable {

    private String szxrwProviderName = "";
    private String szxrwEmail = "";
    private String szxrwPassword = "";
    private String szxrwIDNumber = "";
    private String szxrwMobileNumber = "";
    private String szxrwAddress = "";
    private Bitmap bmxrwImage = null;
    private String szxrwProfileImageName = "";
    private String szxrwProfileImageURL = "";
    private String szxrwDateOfBirth = "";
    private String szxrwServiceName = "";
    private int nxrwUserId = 0;
    private int nxrwProviderId = 0;
    private int nxrwUserType = 0;
    private int nxrwUserActivated = 0;
    private int nxrwServiceProviderActivated = 0;
    private String szxrwToken = "";
    private String szxrwProviderDetailObjectName = "";
    private int nxrwProviderDetailObjectId = 0;
    private ProviderDetailObjects pdosxrwParent = null;

    public ProviderDetailObject()
    {
        this.szxrwEmail = szxrwEmail;
        this.szxrwProviderName = szxrwProviderName;
    }

    public void setParentIsProviderDetailObjects(ProviderDetailObjects objvParent)
    {
        pdosxrwParent = objvParent;
    }

    public ProviderDetailObjects getParentIsProviderDetailObjects()
    {
        return pdosxrwParent;
    }

    public void setProviderDetailObjectName(String szvProviderDetailObjectName)
    {
        szxrwProviderDetailObjectName = szvProviderDetailObjectName;
    }

    public String getProviderDetailObjectName()
    {
        return szxrwProviderDetailObjectName;
    }

    public void setProviderDetailObjectId(int nvCountyId)
    {
        nxrwProviderDetailObjectId = nvCountyId;
    }

    public int getProviderDetailObjectId()
    {
        return nxrwProviderDetailObjectId;
    }

    public void setUserId(int nvUserId)
    {
        this.nxrwUserId = nvUserId;
    }
    public int getUserId()
    {
        return nxrwUserId;
    }

    public void setProviderId(int nvProviderId)
    {
        this.nxrwProviderId = nvProviderId;
    }
    public int getProviderId()
    {
        return nxrwProviderId;
    }

    public void setServiceName(String szvServiceName)
    {
        this.szxrwServiceName = szvServiceName;
    }
    public String getServiceName()
    {
        return szxrwServiceName;
    }

    public void setProviderName(String szvProviderName)
    {
        this.szxrwProviderName = szvProviderName;
    }
    public String getProviderName()
    {
        return szxrwProviderName;
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

    public void setServiceProviderActivated(int nvServiceProviderActivated)
    {
        this.nxrwServiceProviderActivated = nvServiceProviderActivated;
    }
    public int getServiceProviderActivated()
    {
        return nxrwServiceProviderActivated;
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
