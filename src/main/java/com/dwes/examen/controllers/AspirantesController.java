package com.dwes.examen.controllers;

import com.dwes.examen.entities.Aspirante;
import com.dwes.examen.entities.Usuario;
import com.dwes.examen.entities.Voto;
import com.dwes.examen.repositories.AspiranteRepository;
import com.dwes.examen.repositories.UsuarioRepository;
import com.dwes.examen.repositories.VotoRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AspirantesController {

    @Autowired
    VotoRepository votoRepository;
    @Autowired
    AspiranteRepository aspiranteRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    private static final List<String> PERMITTED_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/avif", "image/webp");
    @Autowired
    private HttpServletResponse httpServletResponse;

    @GetMapping("/aspirantes/add")
    public String newAspirante(Model model) {
        model.addAttribute("aspirante", new Aspirante());
        return "aspirantes-new";
    }

    @PostMapping("/aspirantes/add")
    public String newAspirantePost(Model model, @Valid Aspirante aspirante,
                                   BindingResult bindingResult,
                                   @RequestParam("fotoFile") MultipartFile fotoFile,
                                           HttpSession session) throws IOException {
        boolean error = false;
        if (bindingResult.hasErrors()) {
            error = true;
        }
        if(fotoFile.isEmpty() || !PERMITTED_TYPES.contains(fotoFile.getContentType())) {    //Error en la imagen
            model.addAttribute("error", "Error en la imagen, está en blanco o formato no valido");
            error = true;
        }
        if(error) {
            return "aspirantes-new";
        }
        else{
            //Escribimos el archivo en la ruta que lo queramos guardar
            Path ruta = Paths.get("uploads/" + fotoFile.getOriginalFilename());
            byte[] contenidoArchivo = fotoFile.getBytes();
            Files.write(ruta, contenidoArchivo);

            //Ponemos el nombre del archivo en el objeto
            aspirante.setFoto(fotoFile.getOriginalFilename());
            aspiranteRepository.save(aspirante);
            session.setAttribute("mensaje","El aspirante se ha añadido correctamente");
            return "redirect:/aspirantes";
        }
    }

    @GetMapping("/aspirantes/prueba")
    public String aspirantesPrueba(Model model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "aspirantes-prueba";
    }


    @GetMapping("/aspirantes")
    public String aspirantes(Model model, HttpSession session, Principal principal, HttpServletRequest request) {

        Usuario usuario;
        if(principal != null) {
            usuario = usuarioRepository.findByEmail(principal.getName()).get();
            session.setAttribute("usuario", usuario);
            model.addAttribute("usuario", usuario);
        }


        String mensaje = (String)session.getAttribute("mensaje");

        List<Aspirante> aspirantes = aspiranteRepository.findAll();
        model.addAttribute("aspirantes", aspirantes);
        model.addAttribute("mensaje", mensaje);
        session.removeAttribute("mensaje");

        //Si existe la cookie acepto-cookies lo mandamos a la vista para que no muestre el mensaje
        Cookie[] cookies = request.getCookies();
        Boolean existeCookie=false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("acepta-cookies".equals(cookie.getName())) {
                    existeCookie=true;
                }
            }
        }
        model.addAttribute("existeCookie", existeCookie);
        return "aspirantes-list";
    }

    @GetMapping("aspirantes/addVoto/{id}")
    public String addVoto(@PathVariable(name = "id") Long idVoto, RedirectAttributes redirectAttributes,
                            Authentication authentication, Principal principal) {
        Voto voto = new Voto();
        Optional<Aspirante> aspiranteOtp = aspiranteRepository.findById(idVoto);
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(principal.getName());
        if(aspiranteOtp.isPresent() && usuarioOpt.isPresent())
        {
            if(usuarioOpt.get().getVotos().size()<12) {
                voto.setAspirante(aspiranteOtp.get());
                voto.setFechaVoto(LocalDateTime.now());
                voto.setUsuario(usuarioOpt.get());
                votoRepository.save(voto);
            }else{
                redirectAttributes.addFlashAttribute("error", "Ya has votado tramposo");
            }
        }
        else{
            redirectAttributes.addFlashAttribute("error", "No existe el aspirante");
        }
        return "redirect:/aspirantes";
    }

    @GetMapping("/aceptar-cookies")
    public String aceptarCookies(Model model, HttpSession session, HttpServletResponse response) {
        Cookie cookie = new Cookie("acepta-cookies","true");
        cookie.setMaxAge(60*60*25*7);
        response.addCookie(cookie);
        return "redirect:/aspirantes";
    }

}
