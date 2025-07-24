package sum25.hsf302.sedo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sum25.hsf302.sedo.dto.PCBuildDTO;
import sum25.hsf302.sedo.pojo.ComputerDevice;
import sum25.hsf302.sedo.pojo.PCBuild;
import sum25.hsf302.sedo.pojo.User;
import sum25.hsf302.sedo.service.CartService;
import sum25.hsf302.sedo.service.ComputerDeviceService;
import sum25.hsf302.sedo.service.PCBuidService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
public class PCBuilderController {

    @Autowired
    private ComputerDeviceService computerDeviceService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PCBuidService pcBuidService;

    @GetMapping("/pc-builder")
    public String showBuilder(
            @RequestParam(value = "cpuPage", defaultValue = "0") int cpuPage,
            @RequestParam(value = "mainboardPage", defaultValue = "0") int mainboardPage,
            @RequestParam(value = "ramPage", defaultValue = "0") int ramPage,
            @RequestParam(value = "ssdPage", defaultValue = "0") int ssdPage,
            @RequestParam(value = "hddPage", defaultValue = "0") int hddPage,
            @RequestParam(value = "vgaPage", defaultValue = "0") int vgaPage,
            @RequestParam(value = "psuPage", defaultValue = "0") int psuPage,
            @RequestParam(value = "casePage", defaultValue = "0") int casePage,
            @RequestParam(value = "coolerPage", defaultValue = "0") int coolerPage,
            @RequestParam(value = "monitorPage", defaultValue = "0") int monitorPage,
            @RequestParam(value = "keyboardPage", defaultValue = "0") int keyboardPage,
            @RequestParam(value = "mousePage", defaultValue = "0") int mousePage,
            @RequestParam(value = "headphonePage", defaultValue = "0") int headphonePage,
            @RequestParam(value = "speakerPage", defaultValue = "0") int speakerPage,
            Model model, HttpSession session
    ) {
        int pageSize = 4;

        // CPU
        Page<ComputerDevice> cpuPageData = computerDeviceService.findByCategory("CPU", PageRequest.of(cpuPage, pageSize));
        model.addAttribute("cpu", cpuPageData.getContent());
        model.addAttribute("cpuCurrentPage", cpuPage);
        model.addAttribute("cpuTotalPages", cpuPageData.getTotalPages());

        // Mainboard
        Page<ComputerDevice> mainboardPageData = computerDeviceService.findByCategory("Mainboard", PageRequest.of(mainboardPage, pageSize));
        model.addAttribute("mainboard", mainboardPageData.getContent());
        model.addAttribute("mainboardCurrentPage", mainboardPage);
        model.addAttribute("mainboardTotalPages", mainboardPageData.getTotalPages());

        // RAM
        Page<ComputerDevice> ramPageData = computerDeviceService.findByCategory("RAM", PageRequest.of(ramPage, pageSize));
        model.addAttribute("ram", ramPageData.getContent());
        model.addAttribute("ramCurrentPage", ramPage);
        model.addAttribute("ramTotalPages", ramPageData.getTotalPages());

        // Các loại khác tương tự...
        model.addAttribute("ssd", computerDeviceService.findByCategory("SSD", PageRequest.of(ssdPage, pageSize)).getContent());
        model.addAttribute("ssdCurrentPage", ssdPage);
        model.addAttribute("ssdTotalPages", computerDeviceService.findByCategory("SSD", PageRequest.of(ssdPage, pageSize)).getTotalPages());

        model.addAttribute("hdd", computerDeviceService.findByCategory("HDD", PageRequest.of(hddPage, pageSize)).getContent());
        model.addAttribute("hddCurrentPage", hddPage);
        model.addAttribute("hddTotalPages", computerDeviceService.findByCategory("HDD", PageRequest.of(hddPage, pageSize)).getTotalPages());

        model.addAttribute("vga", computerDeviceService.findByCategory("VGA", PageRequest.of(vgaPage, pageSize)).getContent());
        model.addAttribute("vgaCurrentPage", vgaPage);
        model.addAttribute("vgaTotalPages", computerDeviceService.findByCategory("VGA", PageRequest.of(vgaPage, pageSize)).getTotalPages());


        model.addAttribute("psu", computerDeviceService.findByCategory("PSU", PageRequest.of(psuPage, pageSize)).getContent());
        model.addAttribute("psuCurrentPage", psuPage);
        model.addAttribute("psuTotalPages", computerDeviceService.findByCategory("PSU", PageRequest.of(psuPage, pageSize)).getTotalPages());

        model.addAttribute("case", computerDeviceService.findByCategory("Case", PageRequest.of(casePage, pageSize)).getContent());
        model.addAttribute("caseCurrentPage", casePage);
        model.addAttribute("caseTotalPages", computerDeviceService.findByCategory("Case", PageRequest.of(casePage, pageSize)).getTotalPages());

        model.addAttribute("cooling", computerDeviceService.findByCategory("CPU Cooler", PageRequest.of(coolerPage, pageSize)).getContent());
        model.addAttribute("coolerCurrentPage", coolerPage);
        model.addAttribute("coolerTotalPages", computerDeviceService.findByCategory("CPU Cooler", PageRequest.of(coolerPage, pageSize)).getTotalPages());

        model.addAttribute("monitor", computerDeviceService.findByCategory("Monitor", PageRequest.of(monitorPage, pageSize)).getContent());
        model.addAttribute("monitorCurrentPage", monitorPage);
        model.addAttribute("monitorTotalPages", computerDeviceService.findByCategory("Monitor", PageRequest.of(monitorPage, pageSize)).getTotalPages());

        model.addAttribute("keyboard", computerDeviceService.findByCategory("Keyboard", PageRequest.of(keyboardPage, pageSize)).getContent());
        model.addAttribute("keyboardCurrentPage", keyboardPage);
        model.addAttribute("keyboardTotalPages", computerDeviceService.findByCategory("Keyboard", PageRequest.of(keyboardPage, pageSize)).getTotalPages());

        model.addAttribute("mouse", computerDeviceService.findByCategory("Mouse", PageRequest.of(mousePage, pageSize)).getContent());
        model.addAttribute("mouseCurrentPage", mousePage);
        model.addAttribute("mouseTotalPages", computerDeviceService.findByCategory("Mouse", PageRequest.of(mousePage, pageSize)).getTotalPages());

        model.addAttribute("headphones", computerDeviceService.findByCategory("Headset", PageRequest.of(headphonePage, pageSize)).getContent());
        model.addAttribute("headphoneCurrentPage", headphonePage);
        model.addAttribute("headphoneTotalPages", computerDeviceService.findByCategory("Headset", PageRequest.of(headphonePage, pageSize)).getTotalPages());

        model.addAttribute("speakers", computerDeviceService.findByCategory("Speaker", PageRequest.of(speakerPage, pageSize)).getContent());
        model.addAttribute("speakerCurrentPage", speakerPage);
        model.addAttribute("speakerTotalPages", computerDeviceService.findByCategory("Speaker", PageRequest.of(speakerPage, pageSize)).getTotalPages());


        // ✅ Lấy cấu hình build đã chọn (nếu có)
        PCBuild selectedBuild = (PCBuild) session.getAttribute("selectedBuild");

        BigDecimal totalPrice = BigDecimal.ZERO;

        if (selectedBuild != null) {
            model.addAttribute("selected", selectedBuild);
//            session.removeAttribute("selectedBuild"); // Xóa để tránh giữ hoài

            if (selectedBuild.getCpu() != null) totalPrice = totalPrice.add(selectedBuild.getCpu().getPrice());
            if (selectedBuild.getMainboard() != null) totalPrice = totalPrice.add(selectedBuild.getMainboard().getPrice());
            if (selectedBuild.getRam() != null) totalPrice = totalPrice.add(selectedBuild.getRam().getPrice());
            if (selectedBuild.getGpu() != null) totalPrice = totalPrice.add(selectedBuild.getGpu().getPrice());
            if (selectedBuild.getHdd() != null) totalPrice = totalPrice.add(selectedBuild.getHdd().getPrice());
            if (selectedBuild.getSsd() != null) totalPrice = totalPrice.add(selectedBuild.getSsd().getPrice());
            if (selectedBuild.getPsu() != null) totalPrice = totalPrice.add(selectedBuild.getPsu().getPrice());
            if (selectedBuild.getPcCase() != null) totalPrice = totalPrice.add(selectedBuild.getPcCase().getPrice());
            if (selectedBuild.getCooling() != null) totalPrice = totalPrice.add(selectedBuild.getCooling().getPrice());
            if (selectedBuild.getMonitor() != null) totalPrice = totalPrice.add(selectedBuild.getMonitor().getPrice());
            if (selectedBuild.getKeyboard() != null) totalPrice = totalPrice.add(selectedBuild.getKeyboard().getPrice());
            if (selectedBuild.getMouse() != null) totalPrice = totalPrice.add(selectedBuild.getMouse().getPrice());
            if (selectedBuild.getHeadphones() != null) totalPrice = totalPrice.add(selectedBuild.getHeadphones().getPrice());
            if (selectedBuild.getSpeakers() != null) totalPrice = totalPrice.add(selectedBuild.getSpeakers().getPrice());
        }

        model.addAttribute("totalPrice", totalPrice);



        return "pc-builder";
    }


    @PostMapping("/pc-builder/select")
    public String savePcBuild(@ModelAttribute PCBuildDTO dto,
                              @RequestParam(name = "action", required = false) String action,
                              HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        PCBuild build = new PCBuild();
        build.setName(dto.getName());
        build.setUser(user);
        build.setCreatedAt(LocalDateTime.now());

        build.setCpu(getDevice(dto.getCpuId()));
        build.setMainboard(getDevice(dto.getMainboardId()));
        build.setRam(getDevice(dto.getRamId()));
        build.setGpu(getDevice(dto.getVgaId()));
        build.setHdd(getDevice(dto.getHddId()));
        build.setSsd(getDevice(dto.getSsdId()));
        build.setPsu(getDevice(dto.getPsuId()));
        build.setPcCase(getDevice(dto.getCaseId()));
        build.setCooling(getDevice(dto.getCoolingId()));
        build.setMonitor(getDevice(dto.getMonitorId()));
        build.setKeyboard(getDevice(dto.getKeyboardId()));
        build.setMouse(getDevice(dto.getMouseId()));
        build.setHeadphones(getDevice(dto.getHeadphonesId()));
        build.setSpeakers(getDevice(dto.getSpeakersId()));

        pcBuidService.savePCBuild(build);

        // 👉 Nếu là checkout thì thêm vào giỏ và chuyển trang
        if ("checkout".equals(action)) {
            cartService.addBuildToCart(user, build);
            session.removeAttribute("selectedBuild"); // ✅ XÓA sau khi thanh toán
            return "redirect:/cart";
        }

        // 👉 Nếu là cập nhật thì lưu lại để hiển thị tiếp
        session.setAttribute("selectedBuild", build);
        return "redirect:/pc-builder";
    }

    @GetMapping("/pc-builder/search")
    public String showPcBuilderPage(
            @RequestParam(value = "cpuPage", defaultValue = "0") int cpuPage,
            @RequestParam(value = "mainboardPage", defaultValue = "0") int mainboardPage,
            @RequestParam(value = "ramPage", defaultValue = "0") int ramPage,
            @RequestParam(value = "ssdPage", defaultValue = "0") int ssdPage,
            @RequestParam(value = "hddPage", defaultValue = "0") int hddPage,
            @RequestParam(value = "vgaPage", defaultValue = "0") int vgaPage,
            @RequestParam(value = "casePage", defaultValue = "0") int casePage,
            @RequestParam(value = "coolerPage", defaultValue = "0") int coolerPage,
            @RequestParam(value = "keyboardPage", defaultValue = "0") int keyboardPage,
            @RequestParam(value = "mousePage", defaultValue = "0") int mousePage,
            @RequestParam(value = "headphonePage", defaultValue = "0") int headphonePage,
            @RequestParam(value = "speakerPage", defaultValue = "0") int speakerPage,
            @RequestParam(value = "psuPage", defaultValue = "0") int psuPage,
            @RequestParam(value = "monitorPage", defaultValue = "0") int monitorPage,

            @RequestParam(value = "cpuSearch", required = false) String cpuSearch,
            @RequestParam(value = "mainboardSearch", required = false) String mainboardSearch,
            @RequestParam(value = "ramSearch", required = false) String ramSearch,
            @RequestParam(value = "ssdSearch", required = false) String ssdSearch,
            @RequestParam(value = "hddSearch", required = false) String hddSearch,
            @RequestParam(value = "vgaSearch", required = false) String vgaSearch,
            @RequestParam(value = "caseSearch", required = false) String caseSearch,
            @RequestParam(value = "coolerSearch", required = false) String coolerSearch,
            @RequestParam(value = "keyboardSearch", required = false) String keyboardSearch,
            @RequestParam(value = "mouseSearch", required = false) String mouseSearch,
            @RequestParam(value = "headphoneSearch", required = false) String headphoneSearch,
            @RequestParam(value = "speakerSearch", required = false) String speakerSearch,
            @RequestParam(value = "psuSearch", required = false) String psuSearch,
            @RequestParam(value = "monitorSearch", required = false) String monitorSearch,
            @RequestParam(value = "openModal", required = false) String openModal,


            Model model, HttpSession session) {

        Pageable cpuPageable = PageRequest.of(cpuPage, 4);
        Pageable mainboardPageable = PageRequest.of(mainboardPage, 4);
        Pageable ramPageable = PageRequest.of(ramPage, 4);
        Pageable ssdPageable = PageRequest.of(ssdPage, 4);
        Pageable hddPageable = PageRequest.of(hddPage, 4);
        Pageable vgaPageable = PageRequest.of(vgaPage, 4);
        Pageable casePageable = PageRequest.of(casePage, 4);
        Pageable coolerPageable = PageRequest.of(coolerPage, 4);
        Pageable keyboardPageable = PageRequest.of(keyboardPage, 4);
        Pageable mousePageable = PageRequest.of(mousePage, 4);
        Pageable headphonePageable = PageRequest.of(headphonePage, 4);
        Pageable speakerPageable = PageRequest.of(speakerPage, 4);
        Pageable psuPageable = PageRequest.of(psuPage, 4);
        Pageable monitorPageable = PageRequest.of(monitorPage, 4);


        Page<ComputerDevice> cpuList = (cpuSearch != null && !cpuSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("CPU", cpuSearch, cpuPageable)
                : computerDeviceService.findByCategory("CPU", cpuPageable);
        model.addAttribute("cpu", cpuList.getContent());
        model.addAttribute("cpuCurrentPage", cpuList.getNumber());
        model.addAttribute("cpuTotalPages", cpuList.getTotalPages());
        model.addAttribute("cpuSearch", cpuSearch);

        Page<ComputerDevice> mainboardList = (mainboardSearch != null && !mainboardSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Mainboard", mainboardSearch, mainboardPageable)
                : computerDeviceService.findByCategory("Mainboard", mainboardPageable);
        model.addAttribute("mainboard", mainboardList.getContent());
        model.addAttribute("mainboardCurrentPage", mainboardList.getNumber());
        model.addAttribute("mainboardTotalPages", mainboardList.getTotalPages());
        model.addAttribute("mainboardSearch", mainboardSearch);

        Page<ComputerDevice> ramList = (ramSearch != null && !ramSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("RAM", ramSearch, ramPageable)
                : computerDeviceService.findByCategory("RAM", ramPageable);
        model.addAttribute("ram", ramList.getContent());
        model.addAttribute("ramCurrentPage", ramList.getNumber());
        model.addAttribute("ramTotalPages", ramList.getTotalPages());
        model.addAttribute("ramSearch", ramSearch);

        Page<ComputerDevice> ssdList = (ssdSearch != null && !ssdSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("SSD", ssdSearch, ssdPageable)
                : computerDeviceService.findByCategory("SSD", ssdPageable);
        model.addAttribute("ssd", ssdList.getContent());
        model.addAttribute("ssdCurrentPage", ssdList.getNumber());
        model.addAttribute("ssdTotalPages", ssdList.getTotalPages());
        model.addAttribute("ssdSearch", ssdSearch);

        Page<ComputerDevice> hddList = (hddSearch != null && !hddSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("HDD", hddSearch, hddPageable)
                : computerDeviceService.findByCategory("HDD", hddPageable);
        model.addAttribute("hdd", hddList.getContent());
        model.addAttribute("hddCurrentPage", hddList.getNumber());
        model.addAttribute("hddTotalPages", hddList.getTotalPages());
        model.addAttribute("hddSearch", hddSearch);

        Page<ComputerDevice> vgaList = (vgaSearch != null && !vgaSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("VGA", vgaSearch, vgaPageable)
                : computerDeviceService.findByCategory("VGA", vgaPageable);
        model.addAttribute("vga", vgaList.getContent());
        model.addAttribute("vgaCurrentPage", vgaList.getNumber());
        model.addAttribute("vgaTotalPages", vgaList.getTotalPages());
        model.addAttribute("vgaSearch", vgaSearch);

        Page<ComputerDevice> caseList = (caseSearch != null && !caseSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Case", caseSearch, casePageable)
                : computerDeviceService.findByCategory("Case", casePageable);
        model.addAttribute("case", caseList.getContent());
        model.addAttribute("caseCurrentPage", caseList.getNumber());
        model.addAttribute("caseTotalPages", caseList.getTotalPages());
        model.addAttribute("caseSearch", caseSearch);

        Page<ComputerDevice> coolerList = (coolerSearch != null && !coolerSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("CPU Cooler", coolerSearch, coolerPageable)
                : computerDeviceService.findByCategory("CPU Cooler", coolerPageable);
        model.addAttribute("cooling", coolerList.getContent());
        model.addAttribute("coolerCurrentPage", coolerList.getNumber());
        model.addAttribute("coolerTotalPages", coolerList.getTotalPages());
        model.addAttribute("coolerSearch", coolerSearch);

        Page<ComputerDevice> keyboardList = (keyboardSearch != null && !keyboardSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Keyboard", keyboardSearch, keyboardPageable)
                : computerDeviceService.findByCategory("Keyboard", keyboardPageable);
        model.addAttribute("keyboard", keyboardList.getContent());
        model.addAttribute("keyboardCurrentPage", keyboardList.getNumber());
        model.addAttribute("keyboardTotalPages", keyboardList.getTotalPages());
        model.addAttribute("keyboardSearch", keyboardSearch);

        Page<ComputerDevice> mouseList = (mouseSearch != null && !mouseSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Mouse", mouseSearch, mousePageable)
                : computerDeviceService.findByCategory("Mouse", mousePageable);
        model.addAttribute("mouse", mouseList.getContent());
        model.addAttribute("mouseCurrentPage", mouseList.getNumber());
        model.addAttribute("mouseTotalPages", mouseList.getTotalPages());
        model.addAttribute("mouseSearch", mouseSearch);

        Page<ComputerDevice> headphoneList = (headphoneSearch != null && !headphoneSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Headset", headphoneSearch, headphonePageable)
                : computerDeviceService.findByCategory("Headset", headphonePageable);
        model.addAttribute("headphones", headphoneList.getContent());
        model.addAttribute("headphoneCurrentPage", headphoneList.getNumber());
        model.addAttribute("headphoneTotalPages", headphoneList.getTotalPages());
        model.addAttribute("headphoneSearch", headphoneSearch);

        Page<ComputerDevice> speakerList = (speakerSearch != null && !speakerSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Speaker", speakerSearch, speakerPageable)
                : computerDeviceService.findByCategory("Speaker", speakerPageable);
        model.addAttribute("speakers", speakerList.getContent());
        model.addAttribute("speakerCurrentPage", speakerList.getNumber());
        model.addAttribute("speakerTotalPages", speakerList.getTotalPages());
        model.addAttribute("speakerSearch", speakerSearch);

        Page<ComputerDevice> psuList = (psuSearch != null && !psuSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("PSU", psuSearch, psuPageable)
                : computerDeviceService.findByCategory("PSU", psuPageable);
        model.addAttribute("psu", psuList.getContent());
        model.addAttribute("psuCurrentPage", psuList.getNumber());
        model.addAttribute("psuTotalPages", psuList.getTotalPages());
        model.addAttribute("psuSearch", psuSearch);

        Page<ComputerDevice> monitorList = (monitorSearch != null && !monitorSearch.isBlank()) ?
                computerDeviceService.findByCategory_NameAndNameContainingIgnoreCase("Monitor", monitorSearch, monitorPageable)
                : computerDeviceService.findByCategory("Monitor", monitorPageable);
        model.addAttribute("monitor", monitorList.getContent());
        model.addAttribute("monitorCurrentPage", monitorList.getNumber());
        model.addAttribute("monitorTotalPages", monitorList.getTotalPages());
        model.addAttribute("monitorSearch", monitorSearch);

        model.addAttribute("openModal", openModal);

        PCBuild selectedBuild = (PCBuild) session.getAttribute("selectedBuild");

        BigDecimal totalPrice = BigDecimal.ZERO;

        if (selectedBuild != null) {
            model.addAttribute("selected", selectedBuild);

            if (selectedBuild.getCpu() != null) totalPrice = totalPrice.add(selectedBuild.getCpu().getPrice());
            if (selectedBuild.getMainboard() != null) totalPrice = totalPrice.add(selectedBuild.getMainboard().getPrice());
            if (selectedBuild.getRam() != null) totalPrice = totalPrice.add(selectedBuild.getRam().getPrice());
            if (selectedBuild.getGpu() != null) totalPrice = totalPrice.add(selectedBuild.getGpu().getPrice());
            if (selectedBuild.getHdd() != null) totalPrice = totalPrice.add(selectedBuild.getHdd().getPrice());
            if (selectedBuild.getSsd() != null) totalPrice = totalPrice.add(selectedBuild.getSsd().getPrice());
            if (selectedBuild.getPsu() != null) totalPrice = totalPrice.add(selectedBuild.getPsu().getPrice());
            if (selectedBuild.getPcCase() != null) totalPrice = totalPrice.add(selectedBuild.getPcCase().getPrice());
            if (selectedBuild.getCooling() != null) totalPrice = totalPrice.add(selectedBuild.getCooling().getPrice());
            if (selectedBuild.getMonitor() != null) totalPrice = totalPrice.add(selectedBuild.getMonitor().getPrice());
            if (selectedBuild.getKeyboard() != null) totalPrice = totalPrice.add(selectedBuild.getKeyboard().getPrice());
            if (selectedBuild.getMouse() != null) totalPrice = totalPrice.add(selectedBuild.getMouse().getPrice());
            if (selectedBuild.getHeadphones() != null) totalPrice = totalPrice.add(selectedBuild.getHeadphones().getPrice());
            if (selectedBuild.getSpeakers() != null) totalPrice = totalPrice.add(selectedBuild.getSpeakers().getPrice());
        }

        model.addAttribute("totalPrice", totalPrice);

        return "pc-builder";


    }


    private ComputerDevice getDevice(Long id) {
        return (id != null) ? computerDeviceService.findById(id) : null;
    }
}