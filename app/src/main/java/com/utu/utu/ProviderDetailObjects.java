package com.utu.utu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class ProviderDetailObjects{
    private int nxrwProviderDetailObjectId = 0;
    private String szxrwConstituencyName = "";
    private String szxrwProviderDetailObjectName = "";
    private List<ProviderDetailObject> collxProviderDetailObjects = null;

    public ProviderDetailObjects()
    {
        collxProviderDetailObjects = new ArrayList<ProviderDetailObject>();
    }

    public List ProviderDetailObjectCollection()
    {
        return collxProviderDetailObjects;
    }

    public ProviderDetailObject AddProviderDetailObject(String szvKey)
    {
        ProviderDetailObject pdo = new ProviderDetailObject();

        collxProviderDetailObjects.add(collxProviderDetailObjects.size(),pdo);
        pdo.setProviderDetailObjectName(szvKey);
        pdo.setProviderDetailObjectId(collxProviderDetailObjects.size());
        pdo.setParentIsProviderDetailObjects(this);

        return pdo;
    }
    public ProviderDetailObject ItemIsProviderDetailObject(String szvKey)
    {
        for (ProviderDetailObject c:collxProviderDetailObjects) {
            if(szvKey.toLowerCase().equals(c.getProviderDetailObjectName().toLowerCase()))
            {
                return c;
            }
        }
        return null;
    }

    public int ProviderDetailObjectsCount()
    {
        return collxProviderDetailObjects.size();
    }

    public void RemoveProviderDetailObject(int nvProviderDetailObjectId)
    {
        collxProviderDetailObjects.remove(nvProviderDetailObjectId);
    }

    public void RemoveProviderDetailObject(String szvKey)
    {
        int nProviderDetailObjectId = 0;
        boolean bFound = false;
        for (ProviderDetailObject w:collxProviderDetailObjects) {
            if(szvKey.toLowerCase().equals(w.getProviderDetailObjectName().toLowerCase()))
            {
                bFound = true;
                nProviderDetailObjectId = w.getProviderDetailObjectId();
                break;
            }
        }

        if(bFound) {
            collxProviderDetailObjects.remove(nProviderDetailObjectId);
        }
    }

    public void RemoveAllProviderDetailObjects()
    {
        collxProviderDetailObjects.clear();
    }

    public boolean ProviderDetailObjectExists(String szvKey)
    {
        for (ProviderDetailObject w:collxProviderDetailObjects) {
            if(szvKey.toLowerCase().equals(w.getProviderDetailObjectName().toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }

    public ProviderDetailObject ItemIsProviderDetailObject(int nvProviderDetailObjectId)
    {
        return collxProviderDetailObjects.get(nvProviderDetailObjectId);
    }
}
