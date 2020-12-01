package com.thiha.hswagata.ui.home;

public class rvmodel {

    String ItemName, Category, COlor, Itemimage,Postid,description,StoreName,StoreUid,Storelogo,Owner,StorePh,UsageTime;
    int Leftitem, Price;
    public rvmodel() {
    }

    public rvmodel(String itemName, String category, String COlor, String itemimage, String postid, String description, String storeName, String storeUid, String storelogo, String owner, String storeph, String usageTime, int leftitem, int price) {
        ItemName = itemName;
        Category = category;
        this.COlor = COlor;
        Itemimage = itemimage;
        Postid = postid;
        this.description = description;
        StoreName = storeName;
        StoreUid = storeUid;
        Storelogo = storelogo;
        Owner = owner;
        StorePh = storeph;
        UsageTime = usageTime;
        Leftitem = leftitem;
        Price = price;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getCOlor() {
        return COlor;
    }

    public void setCOlor(String COlor) {
        this.COlor = COlor;
    }

    public String getItemimage() {
        return Itemimage;
    }

    public void setItemimage(String itemimage) {
        Itemimage = itemimage;
    }

    public String getPostid() {
        return Postid;
    }

    public void setPostid(String postid) {
        Postid = postid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreUid() {
        return StoreUid;
    }

    public void setStoreUid(String storeUid) {
        StoreUid = storeUid;
    }

    public String getStorelogo() {
        return Storelogo;
    }

    public void setStorelogo(String storelogo) {
        Storelogo = storelogo;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getStoreph() {
        return StorePh;
    }

    public void setStoreph(String storeph) {
        StorePh = storeph;
    }

    public String getUsageTime() {
        return UsageTime;
    }

    public void setUsageTime(String usageTime) {
        UsageTime = usageTime;
    }

    public int getLeftitem() {
        return Leftitem;
    }

    public void setLeftitem(int leftitem) {
        Leftitem = leftitem;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
