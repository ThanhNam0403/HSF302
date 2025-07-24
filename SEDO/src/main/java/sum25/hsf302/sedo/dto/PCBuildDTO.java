package sum25.hsf302.sedo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PCBuildDTO {
    private String name;

    private Long cpuId;
    private Long mainboardId;
    private Long ramId;
    private Long vgaId;
    private Long hddId;
    private Long ssdId;
    private Long psuId;
    private Long caseId;
    private Long coolingId;
    private Long monitorId;
    private Long keyboardId;
    private Long mouseId;
    private Long headphonesId;
    private Long speakersId;
}
