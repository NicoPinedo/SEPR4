package com.drtn.game.enums;

/**
 * Created by Kieran on 24/02/2017.
 */
public class ResourceTypeEnum {

    public enum ResourceType{
        FOOD, ORE, ENERGY
    }

    ResourceType type;

    public ResourceTypeEnum(ResourceType type){
        this.type = type;
    }
}
