package com.hx.mode.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Experience implements Cloneable{
    private String company;
    private String address;

    @Override
    protected Object clone(){
        Experience experience = null;
        try {
            experience = (Experience) super.clone();
            experience.setCompany(company);
            experience.setAddress(address);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return experience;
    }
}
