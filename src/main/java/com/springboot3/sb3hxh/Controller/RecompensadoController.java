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
@RequestMapping("/recompensados")
public class RecompensadoController {

    private RecompensadoService recompensadoService;
    private HunterService hunterService;
    private RecompensaService recompensaService;

    public RecompensadoController(RecompensadoService theRecompensadoService, HunterService theHunterService, RecompensaService theRecompensaService){
        recompensadoService = theRecompensadoService;
        hunterService = theHunterService;
        recompensaService = theRecompensaService;
    }

    @GetMapping("/list")
    public String listarRecompensados(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensadoEntity> recompensadoEntity = recompensadoService.indexPagination(page -1, size);
        int totalItems = recompensadoService.totalRecompensados();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensados", recompensadoEntity);
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/filtrar-recompensado")
    public String filtrarRecompensado(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensadoEntity> recompensadoEntity;
        int totalItems;
        if (search != null && !search.isEmpty()) {
            recompensadoEntity = recompensadoService.searchRecompensado(search, page -1, size);
            totalItems = recompensadoService.totalRecompensadosBySearch(search);
        } else {
            recompensadoEntity = recompensadoService.indexPagination(page -1, size);
            totalItems = recompensadoService.totalRecompensados();
        }
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensados", recompensadoEntity);
        return "/recompensado/list-recompensados";
    }

    @GetMapping("/form-create-recompensado")
    public String formCreateRecompensado(Model model){
        RecompensadoEntity recompesadoModel = new RecompensadoEntity();
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("recompensado", recompesadoModel);
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        return "/recompensado/create-recompensado";
    }

    @PostMapping("/create-recompensado")
    public String createRecompensado(@ModelAttribute("recompensado") @Valid RecompensadoEntity recompensadoEntity, BindingResult bindingResult, Model model) {
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        if (bindingResult.hasErrors()) {
            return "/recompensado/create-recompensado";
        } else {
            recompensadoService.create(recompensadoEntity);
            return "redirect:/recompensados/list";
        }
    }

    @GetMapping("/form-update-recompensado/{id}")
    public String formUpdateRecompensado(@PathVariable("id") int id, Model model) {
        RecompensadoEntity recompensadoEntity = recompensadoService.read(id);
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("recompensado", recompensadoEntity);
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        if (recompensadoEntity != null) {
            return "/recompensado/update-recompensado";
        } else {
            return "redirect:/recompensados/list";
        }
    }

    @PostMapping("/update-recompensados/{id}")
    public String updateRecompensado(@PathVariable("id") int id, @ModelAttribute("recompensado") @Valid RecompensadoEntity recompensadoEntity, BindingResult bindingResult, Model model) {
        List<HunterEntity> hunterEntity = hunterService.index();
        List<RecompensaEntity> recompensaEntity = recompensaService.index();
        List<RecompensaEntity> recompensasDisponiveis = new ArrayList<>();
        for (RecompensaEntity recompensa : recompensaEntity) {
            boolean jaAssociado = false;
            for (RecompensadoEntity recompensado : recompensadoService.index()) {
                if (recompensado.getRecompensa_id().equals(recompensa)) {
                    jaAssociado = true;
                    break;
                }
            }
            if (!jaAssociado) {
                recompensasDisponiveis.add(recompensa);
            }
        }
        model.addAttribute("hunter", hunterEntity);
        model.addAttribute("recompensa", recompensasDisponiveis);
        if (bindingResult.hasErrors()) {
            return "/recompensado/update-recompensado";
        } else {
            recompensadoEntity.setId(id);
            recompensadoService.update(recompensadoEntity);
            return "redirect:/recompensados/list";
        }
    }

    @GetMapping("/trash-recompensado/{id}")
    public String trashRecompensado(@PathVariable("id") int id) {
        recompensadoService.trash(id);
        return "redirect:/recompensados/list";
    }

    @GetMapping("/trash-list-recompensado")
    public String listarTrashRecompensados(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensadoEntity> recompensadoEntity = recompensadoService.indexTrash(page -1, size);
        int totalItems = recompensadoService.totalRecompensados();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensados", recompensadoEntity);
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/filtrar-recompensado-trash")
    public String filtrarRecompensadoTrash(@RequestParam(name = "search", required = false) String search, Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<RecompensadoEntity> recompensadoEntity;
        int totalItems;
        if (search != null && !search.isEmpty()) {
            recompensadoEntity = recompensadoService.searchRecompensadoTrash(search, page -1, size);
            totalItems = recompensadoService.totalRecompensadosTrashBySearch(search);
        } else {
            recompensadoEntity = recompensadoService.indexTrash(page -1, size);
            totalItems = recompensadoService.totalRecompensados();
        }
        int totalPages = (int) Math.ceil((double) totalItems / size);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("recompensados", recompensadoEntity);
        return "/recompensado/trash-recompensado";
    }

    @GetMapping("/restore-recompensado/{id}")
    public String restoreRecompensado(@PathVariable("id") int id, Model model) {
        recompensadoService.restore(id);
        return "redirect:/recompensados/trash-list-recompensado";
    }

    @GetMapping("/delete-recompensado/{id}")
    public String deleteRecompensado(@PathVariable("id") int id) {
        recompensadoService.delete(id);
        return "redirect:/recompensados/trash-list-recompensado";
    }

}