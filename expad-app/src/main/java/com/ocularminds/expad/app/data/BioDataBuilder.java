package com.ocularminds.expad.app.data;

import com.ocularminds.expad.DataBuilder;
import com.ocularminds.expad.model.CustomizedCard;
import java.util.Base64;

public class BioDataBuilder implements DataBuilder {

    public static String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><EXPAD>\r\n";
    public static String FOOTER = "</EXPAD>";
    CustomizedCard card;

    public BioDataBuilder(final CustomizedCard card) {
        this.card = card;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("<CustomizedCard>\r\r\n");
        addNode(sb, "RegistrationID", card.getId());
        addNode(sb, "MATRIC", card.getNo());
        addNode(sb, "FirstName", card.getFirstName());
        addNode(sb, "MiddleName", card.getMiddleName());
        addNode(sb, "LastName", card.getSurname());
        addNode(sb, "Gender", card.getSex());
        addNode(sb, "RegistrationType", card.getType());
        addNode(sb, "RegistrationDate", card.getDate());
        addNode(sb, "Institute", card.getInstitute());
        addNode(sb, "Faculty", card.getFaculty());
        addNode(sb, "Department", card.getDepartment());
        addNode(sb, "Position", card.getPosition());
        addNode(sb, "Session", card.getSession());
        addNode(sb, "DOB", card.getDob());
        addNode(sb, "Passport", Base64.getEncoder().encode(card.getPhoto()));
        addNode(sb, "SIGNATURE", Base64.getEncoder().encode(card.getSignature()));
        sb.append("</CustomizedCard>\r\n");
        return sb.toString();
    }

    private static void addNode(StringBuilder sb, String name, Object value) {
        sb.append("<");
        sb.append(name);
        sb.append(">");
        sb.append(value.toString());
        sb.append("</");
        sb.append(name);
        sb.append(">\r\n");
    }
}
