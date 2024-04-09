package com.springboot3.sb3hxh.Controller;

import com.springboot3.sb3hxh.Entity.*;
import com.springboot3.sb3hxh.Service.*;
import jakarta.validation.*;
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
    public String listarHunters(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<HunterEntity> hunterEntity = hunterService.indexPagination(page -1, size);
        int totalItems = hunterService.totalHunters();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hunters", hunterEntity);
        return "/hunter/list-hunters";
    }

    @GetMapping("/filtrar-hunter")
    public String filtrarHunter(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<HunterEntity> hunterEntity;
        if (search != null && !search.isEmpty()) {
            hunterEntity = hunterService.searchHunter(search, page -1, size);
        } else {
            hunterEntity = hunterService.indexPagination(page -1, size);
        }
        int totalItems = hunterService.totalHunters();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hunters", hunterEntity);
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
            return "redirect:/hunters/list";
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
            return "redirect:/hunters/list";
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
            return "redirect:/hunters/list";
        }
    }

    @GetMapping("/trash-hunter/{id}")
    public String trashHunter(@PathVariable("id") int id) {
        hunterService.trash(id);
        return "redirect:/hunters/list";
    }

    @GetMapping("/trash-list-hunter")
    public String listarTrashHunters(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<HunterEntity> hunterEntity = hunterService.indexTrash(page -1, size);
        int totalItems = hunterService.totalHunters();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hunters", hunterEntity);
        return "/hunter/trash-hunter";
    }

    @GetMapping("/filtrar-hunter-trash")
    public String filtrarHunterTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<HunterEntity> hunterEntity;
        if (search != null && !search.isEmpty()) {
            hunterEntity = hunterService.searchHunterTrash(search, page -1, size);
        } else {
            hunterEntity = hunterService.indexTrash(page -1, size);
        }
        int totalItems = hunterService.totalHunters();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("hunters", hunterEntity);
        return "/hunter/trash-hunter";
    }

    @GetMapping("/restore-hunter/{id}")
    public String restoreHunter(@PathVariable("id") int id, Model model) {
        hunterService.restore(id);
        return "redirect:/hunters/trash-list-hunter";
    }

    @GetMapping("/delete-hunter/{id}")
    public String deleteHunter(@PathVariable("id") int id) {
        hunterService.delete(id);
        return "redirect:/hunters/trash-list-hunter";
    }

}