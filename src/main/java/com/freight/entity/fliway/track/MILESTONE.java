package com.freight.entity.fliway.track;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MILESTONE") //XML文件中的根标识
public class MILESTONE {
    public List<EVENT> EVENT;
}
