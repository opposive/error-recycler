package com.thiha.hswagata.ui.dashboard.adapternmodel;

public class StoreModel {

    String UID,StoreLogo,StoreName,StoreType,Phone,Owner,Ownerprofile;

    public StoreModel() {
    }

    public StoreModel(String UID, String storeLogo, String storeName, String storeType, String phone, String owner, String Ownerprofile) {
        this.UID = UID;
        StoreLogo = storeLogo;
        StoreName = storeName;
        StoreType = storeType;
        Phone = phone;
        Owner = owner;
        Ownerprofile = Ownerprofile;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getStoreLogo() {
        return StoreLogo;
    }

    public void setStoreLogo(String storeLogo) {
        StoreLogo = storeLogo;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreType() {
        return StoreType;
    }

    public void setStoreType(String storeType) {
        StoreType = storeType;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getOwnerprofile() {
        return Ownerprofile;
    }

    public void setOwnerprofile(String Ownerprofile) {
        Ownerprofile = Ownerprofile;
    }
}
