package com.example.bigfootone.mobilepoebuddy;

import java.io.Serializable;

/**
 * Created by David Stuart on 30/11/2016.
 * S1313657
 */

public class SingleUniqueItem implements Serializable {

    private int itemID;
    private String itemCategory;
    private String itemSubCategory;
    private String itemBaseType;
    private String itemUniqueName;
    private String itemDescription;
    private String itemArmourValue;
    private String itemEvasionValue;
    private String itemEnergyShieldValue;
    private String itemFlavourText;
    private int itemItemLevel;
    private Boolean itemFavourite;
    private String itemImageLink;

    private static final long serialVersionUiD = 0L;

    public SingleUniqueItem() {
    }

    public int getItemID()
    {
        return itemID;
    }

    public void setItemID(int ItemID)
    {
        this.itemID = ItemID;
    }

    public String getItemCategory()
    {
        return  itemCategory;
    }

    public void setItemCategory(String itemCategory)
    {
        this.itemCategory = itemCategory;
    }

    public String getItemSubCategory()
    {
        return itemSubCategory;
    }

    public void setItemSubCategory(String itemSubCategory)
    {
        this.itemSubCategory = itemSubCategory;
    }

    public String getItemBaseType()
    {
        return itemBaseType;
    }

    public void setItemBaseType(String itemBaseType)
    {
        this.itemBaseType = itemBaseType;
    }

    public String getItemUniqueName()
    {
        return itemUniqueName;
    }

    public void setItemUniqueName(String itemUniqueName)
    {
        this.itemUniqueName = itemUniqueName;
    }

    public String getItemDescription()
    {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription)
    {
        this.itemDescription = itemDescription;
    }

    public String getItemArmourValue()
    {
        return itemArmourValue;
    }

    public void setItemArmourValue(String itemArmourValue)
    {
        this.itemArmourValue = itemArmourValue;
    }

    public String getItemEvasionValue()
    {
        return itemEvasionValue;
    }

    public void setItemEvasionValue(String itemEvasionValue)
    {
        this.itemEvasionValue = itemEvasionValue;
    }

    public String getItemEnergyShieldValue()
    {
        return itemEnergyShieldValue;
    }

    public void setItemEnergyShieldValue(String itemEnergyShieldValue)
    {
        this.itemEnergyShieldValue = itemEnergyShieldValue;
    }

    public String getItemFlavourText()
    {
        return itemFlavourText;
    }

    public void setItemFlavourText(String itemFlavourText)
    {
        this.itemFlavourText = itemFlavourText;
    }

    public int getItemItemLevel()
    {
        return itemItemLevel;
    }

    public void setItemItemLevel(int itemItemLevel)
    {
        this.itemItemLevel = itemItemLevel;
    }

    public Boolean getItemFavourite()
    {
        return itemFavourite;
    }

    public void setItemFavourite(Boolean itemFavourite)
    {
        this.itemFavourite = itemFavourite;
    }

    public String getItemImageLink()
    {
        return itemImageLink;
    }

    public void setItemImageLink(String itemImageLink)
    {
        this.itemImageLink = itemImageLink;
    }
}
