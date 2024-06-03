package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import jakarta.validation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/hunters")
public class HunterController {

    private HunterService hunterService;
    private TipoHunterService tipoHunterService;
    private TipoSanguineoService tipoSanguineoService;
    private TipoNenService tipoNenService;

    public HunterController(HunterService theHunterService, TipoHunterService theTipoHunterService, TipoNenService theTipoNenService, TipoSanguineoService theTipoSanguineoService){
        hunterService = theHunterService;
        tipoHunterService = theTipoHunterService;
        tipoNenService = theTipoNenService;
        tipoSanguineoService = theTipoSanguineoService;
    }

    @GetMapping("/list")
    public String listarHunters(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<HunterEntity> hunterPage = hunterService.indexPagination(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        return "/hunter/list-hunters";
    }

    @GetMapping("/filtrar-hunter")
    public String filtrarHunter(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<HunterEntity> hunterPage = (search != null && !search.isEmpty()) ? hunterService.searchHunter(search, page, size) : hunterService.indexPagination(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/hunter/list-hunters";
    }

    @GetMapping("/form-create-hunter")
    public String formCreateHunter(Model model){
        HunterEntity hunterEntity = new HunterEntity();
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        return "/hunter/create-hunter";
    }

    @PostMapping("/create-hunter")
    public String createHunter(@ModelAttribute("hunter") @Valid HunterEntity hunterEntity, BindingResult bindingResult, Model model) {
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        if (bindingResult.hasErrors()) {
            return "/hunter/create-hunter";
        } else {
            hunterService.create(hunterEntity);
            return "redirect:/hunters/list?page=0&size=5";
        }
    }

    @GetMapping("/form-update-hunter/{id}")
    public String formUpdateHunter(@PathVariable("id") int id, Model model) {
        HunterEntity hunterEntity = hunterService.read(id);
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        if (hunterEntity != null) {
            return "/hunter/update-hunter";
        } else {
            return "redirect:/hunters/list?page=0&size=5";
        }
    }

    @PostMapping("/update-hunter/{id}")
    public String updateHunter(@PathVariable("id") int id, @ModelAttribute("hunter") @Valid HunterEntity hunterEntity, BindingResult bindingResult, Model model) {
        List<TipoHunterEntity> tipoHunterEntity = tipoHunterService.index();
        List<TipoNenEntity> tipoNenEntity = tipoNenService.index();
        List<TipoSanguineoEntity> tipoSanguineoEntity = tipoSanguineoService.index();
        model.addAttribute("tipo_hunter", tipoHunterEntity);
        model.addAttribute("tipo_nen", tipoNenEntity);
        model.addAttribute("tipo_sanguineo", tipoSanguineoEntity);
        if (bindingResult.hasErrors()) {
            return "/hunter/update-hunter";
        } else {
            hunterEntity.setId(id);
            hunterService.update(hunterEntity);
            return "redirect:/hunters/list?page=0&size=5";
        }
    }

    @GetMapping("/trash-hunter/{id}")
    public String trashHunter(@PathVariable("id") int id) {
        hunterService.trash(id);
        return "redirect:/hunters/list?page=0&size=5";
    }

    @GetMapping("/trash-list-hunter")
    public String listarTrashHunters(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<HunterEntity> hunterPage = hunterService.indexTrash(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        return "/hunter/trash-hunter";
    }

    @GetMapping("/filtrar-hunter-trash")
    public String filtrarHunterTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<HunterEntity> hunterPage = (search != null && !search.isEmpty()) ? hunterService.searchHunterTrash(search, page, size) : hunterService.indexTrash(page, size);
        model.addAttribute("hunters", hunterPage.getContent());
        model.addAttribute("currentPage", hunterPage.getNumber());
        model.addAttribute("totalPages", hunterPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/hunter/trash-hunter";
    }

    @GetMapping("/restore-hunter/{id}")
    public String restoreHunter(@PathVariable("id") int id, Model model) {
        hunterService.restore(id);
        return "redirect:/hunters/trash-list-hunter?page=0&size=5";
    }

    @GetMapping("/delete-hunter/{id}")
    public String deleteHunter(@PathVariable("id") int id) {
        hunterService.delete(id);
        return "redirect:/hunters/trash-list-hunter?page=0&size=5";
    }

}