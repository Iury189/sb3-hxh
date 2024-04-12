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
@RequestMapping("/recompensas")
public class RecompensaController {

    private RecompensaService recompensaService;

    public RecompensaController(RecompensaService theRecompensaService){
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensas(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensaEntity> recompensaEntity = recompensaService.indexPagination(page -1, size);
        int totalItems = recompensaService.totalRecompensas();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensas", recompensaEntity);
        return "/recompensa/list-recompensas";
    }

    @GetMapping("/filtrar-recompensa")
    public String filtrarRecompensa(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensaEntity> recompensaEntity;
        int totalItems;
        if (search != null && !search.isEmpty()) {
            recompensaEntity = recompensaService.searchRecompensa(search, page -1, size);
            totalItems = recompensaService.totalRecompensasBySearch(search);
        } else {
            recompensaEntity = recompensaService.indexPagination(page -1, size);
            totalItems = recompensaService.totalRecompensas();
        }
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensas", recompensaEntity);
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
            return "redirect:/recompensas/list";
        }
    }

    @GetMapping("/form-update-recompensa/{id}")
    public String formUpdateRecompensa(@PathVariable("id") int id, Model model) {
        RecompensaEntity recompensaEntity = recompensaService.read(id);
        if (recompensaEntity != null) {
            model.addAttribute("recompensa", recompensaEntity);
            return "/recompensa/update-recompensa";
        } else {
            return "redirect:/recompensas/list";
        }
    }

    @PostMapping("/update-recompensa/{id}")
    public String updateRecompensa(@PathVariable("id") int id, @ModelAttribute("recompensa") @Valid RecompensaEntity recompensaEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/recompensa/update-recompensa";
        } else {
            recompensaEntity.setId(id);
            recompensaService.update(recompensaEntity);
            return "redirect:/recompensas/list";
        }
    }

    @GetMapping("/trash-recompensa/{id}")
    public String trashRecompensa(@PathVariable("id") int id) {
        recompensaService.trash(id);
        return "redirect:/recompensas/list";
    }

    @GetMapping("/trash-list-recompensa")
    public String listarTrashRecompensas(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensaEntity> recompensaEntity = recompensaService.indexTrash(page -1, size);
        int totalItems = recompensaService.totalRecompensas();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensas", recompensaEntity);
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/filtrar-recompensa-trash")
    public String filtrarRecompensaTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensaEntity> recompensaEntity;
        int totalItems;
        if (search != null && !search.isEmpty()) {
            recompensaEntity = recompensaService.searchRecompensaTrash(search, page -1, size);
            totalItems = recompensaService.totalRecompensasTrashBySearch(search);
        } else {
            recompensaEntity = recompensaService.indexTrash(page -1, size);
            totalItems = recompensaService.totalRecompensas();
        }
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensas", recompensaEntity);
        return "/recompensa/trash-recompensa";
    }

    @GetMapping("/restore-recompensa/{id}")
    public String restoreRecompensa(@PathVariable("id") int id, Model model) {
        recompensaService.restore(id);
        return "redirect:/recompensas/trash-list-recompensa";
    }

    @GetMapping("/delete-recompensa/{id}")
    public String deleteRecompensa(@PathVariable("id") int id) {
        recompensaService.delete(id);
        return "redirect:/recompensas/trash-list-recompensa";
    }

}