package com.tatvasoft.beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.tatvasoft.entities.User;

@FacesConverter(value = "userConverter")
public class PickListUserConverter implements Converter {

	@Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
        @SuppressWarnings("unchecked")
		DualListModel<User> model = (DualListModel<User>) ((PickList) comp).getValue();
        for (User employee : model.getSource()) {
            if (employee.getUserName().equals(value)) {
                return employee;
            }
        }
        for (User employee : model.getTarget()) {
            if (employee.getUserName().equals(value)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
        return ((User) value).getUserName();
    }
	
}
