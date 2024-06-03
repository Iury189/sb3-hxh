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
@RequestMapping("/recompensas")
public class RecompensaController {

    private RecompensaService recompensaService;

    public RecompensaController(RecompensaService theRecompensaService){
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensas(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = recompensaService.indexPagination(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        return "/recompensa/list-recompensas";
    }

    @GetMapping("/filtrar-recompensa")
    public String filtrarRecompensa(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = (search != null && !search.isEmpty()) ? recompensaService.searchRecompensa(search, page, size) : recompensaService.indexPagination(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensa/list-recompensas";
    }

    @GetMapping("/form-create-recompensa")
    public String formCreateRecompensa(Model model){
        RecompensaEntity recompensaEntity = new RecompensaEntity();
        model.addAttribute("recompensa", recompensaEntity);
        return "/recompensa/create-recompensa";
    }

    @PostMapping("/create-recompensa")
    public String createRecompensa(@ModelAttribute("recompensa") @Valid RecompensaEntity recompensaEntity, BindingResult bindingResult) {
        System.out.println(recompensaEntity);
        if (bindingResult.hasErrors()) {
            return "/recompensa/create-recompensa";
        } else {
            recompensaService.create(recompensaEntity);
            return "redirect:/recompensas/list?page=0&size=5";
        }
    }

    @GetMapping("/form-update-recompensa/{id}")
    public String formUpdateRecompensa(@PathVariable("id") int id, Model model) {
        RecompensaEntity recompensaEntity = recompensaService.read(id);
        if (recompensaEntity != null) {
            model.addAttribute("recompensa", recompensaEntity);
            return "/recompensa/update-recompensa";
        } else {
            return "redirect:/recompensas/list?page=0&size=5";
        }
    }

    @PostMapping("/update-recompensa/{id}")
    public String updateRecompensa(@PathVariable("id") int id, @ModelAttribute("recompensa") @Valid RecompensaEntity recompensaEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/recompensa/update-recompensa";
        } else {
            recompensaEntity.setId(id);
            recompensaService.update(recompensaEntity);
            return "redirect:/recompensas/list?page=0&size=5";
        }
    }

    @GetMapping("/trash-recompensa/{id}")
    public String trashRecompensa(@PathVariable("id") int id) {
        recompensaService.trash(id);
        return "redirect:/recompensas/list?page=0&size=5";
    }

    @GetMapping("/trash-list-recompensa")
    public String listarTrashRecompensas(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = recompensaService.indexTrash(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/filtrar-recompensa-trash")
    public String filtrarRecompensaTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Page<RecompensaEntity> recompensaPage = (search != null && !search.isEmpty()) ? recompensaService.searchRecompensaTrash(search, page, size) : recompensaService.indexTrash(page, size);
        model.addAttribute("recompensas", recompensaPage.getContent());
        model.addAttribute("currentPage", recompensaPage.getNumber());
        model.addAttribute("totalPages", recompensaPage.getTotalPages());
        model.addAttribute("search", search);
        model.addAttribute("size", size);
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/restore-recompensa/{id}")
    public String restoreRecompensa(@PathVariable("id") int id, Model model) {
        recompensaService.restore(id);
        return "redirect:/recompensas/trash-list-recompensa?page=0&size=5";
    }

    @GetMapping("/delete-recompensa/{id}")
    public String deleteRecompensa(@PathVariable("id") int id) {
        recompensaService.delete(id);
        return "redirect:/recompensas/trash-list-recompensa?page=0&size=5";
    }

}