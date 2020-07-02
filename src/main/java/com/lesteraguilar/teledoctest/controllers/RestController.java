package com.lesteraguilar.teledoctest.controllers;

import com.lesteraguilar.teledoctest.models.entity.Club;
import com.lesteraguilar.teledoctest.models.entity.Futbolista;
import com.lesteraguilar.teledoctest.models.entity.Usuario;
import com.lesteraguilar.teledoctest.models.services.IClubService;
import com.lesteraguilar.teledoctest.models.services.IFutbolistaService;
import com.lesteraguilar.teledoctest.models.services.IUsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.lesteraguilar.teledoctest.security.SecurityConstants.EXPIRATION_TIME;
import static com.lesteraguilar.teledoctest.security.SecurityConstants.SECRET;

@RequestMapping("/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private IFutbolistaService futbolistaService;

    @Autowired
    private IClubService clubService;

    @Autowired
    private IUsuarioService usuarioService;


    //Response for ResponseEntity
    private Map<String, Object> response = new HashMap<>();


    //Login

    @PostMapping("/login")
    public ResponseEntity<?> getUsuario(@RequestHeader String username, @RequestHeader String password){

        this.response = new HashMap<>();
        String tokenResponse = getUserToken(username);
        Usuario usuario;
        usuario = usuarioService.findByUsernameAndPassword(username,password);

        try{

            if(null == usuario){
                response.put("Error", "El usuario no existe");
                response.put("Mensaje", "Usuario No Autorizado");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }else{
                usuario.setUsername(username);
                usuario.setToken(tokenResponse);
            }

        }catch(DataAccessException e){
            response.put("mensaje", "Error: no se pudo acceder al dato");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Usuario Autorizado");
        response.put("Usuario", usuario);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    //Token Access

    private String getUserToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String secretKey = SECRET;
        String generatedToken = Jwts
                .builder()
                .setSubject(username)
                .setId("idforthegiventoken")
                .claim("authorities", grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS384, secretKey.getBytes()).compact();

        return generatedToken;
    }

    //Futbolsitas

    @GetMapping("/futbolistas")
    @ResponseStatus(HttpStatus.OK)
    public List<Futbolista> finAll(){
        return futbolistaService.findAll();
    }

    @PostMapping("/getFutbolista")
    public ResponseEntity<?> getFutbolista(@RequestHeader Long id){

        Futbolista futbolista;

        this.response = new HashMap<>();

        try{
            futbolista = futbolistaService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error: no se pudo acceder al dato");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(futbolista == null){
            response.put("mensaje", "Error: el futbolista con ID: (".concat(id.toString().concat(") no existe")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Futbolista>(futbolista, HttpStatus.OK);
    }

    @PostMapping("/futbolistas")
    public ResponseEntity<?> saveFutbolista(@RequestBody Futbolista futbolista, @RequestHeader Long idClub){

        Futbolista newFutbolista = null;
        this.response = new HashMap<>();

        Club clubFutbolista;

        try{
            clubFutbolista = clubService.findById(idClub);
            if(null != clubFutbolista){
                futbolista.setClub(clubFutbolista);
                newFutbolista = futbolistaService.save(futbolista);
            }

        }catch (DataAccessException e){
            response.put("mensaje", "Error: no se pudo acceder al dato");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "futbolista creado con éxito");
        response.put("futbolista", newFutbolista);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);

    }

    //Club

    @GetMapping("/clubes")
    @ResponseStatus(HttpStatus.OK)
    public List<Club> findAll(){
        return clubService.findAll();
    }

    @GetMapping("/futbolistas/{idClub}")
    public ResponseEntity<?> getFutbolistaByClub(@PathVariable Long idClub){

        Club futbolistaClub;
        String nombreClub;
        this.response = new HashMap<>();
        List<Futbolista> futbolistas = new ArrayList<>();
        try{

            futbolistaClub = clubService.findById(idClub);
            nombreClub = clubService.findById(idClub).getNombre();

            for(Futbolista fut:futbolistaClub.getFutbolistas()){
                futbolistas.add(fut);
            }

        }catch(DataAccessException e){
            response.put("mensaje", "Error: no se pudo acceder al dato");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        response.put("Futbolistas del Club: " .concat(nombreClub), futbolistas);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/futbolistas/{idFutbolista}")
    public ResponseEntity<?> deleteFutbolistaByClub(@PathVariable Long idFutbolista){
        Futbolista futbolistaClub;
        Club clubFutbolista;
        this.response = new HashMap<>();

        try{
            futbolistaClub = futbolistaService.findById(idFutbolista);
            clubFutbolista = futbolistaClub.getClub();
            futbolistaService.delete(idFutbolista);
        } catch(DataAccessException e){
            response.put("mensaje", "Error: no se pudo acceder al dato");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El futbolista ".concat(futbolistaClub.getNombre()) + " ".concat(futbolistaClub.getApellido()) + " ha sido eliminado con exito del Club: ".concat(clubFutbolista.getNombre()));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }




}
