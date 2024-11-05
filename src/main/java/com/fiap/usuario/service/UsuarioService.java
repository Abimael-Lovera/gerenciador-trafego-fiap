package com.fiap.usuario.service;

import com.fiap.usuario.dto.UsuarioCreateDTO;
import com.fiap.usuario.dto.UsuarioUpdateDTO;
import com.fiap.usuario.dto.UsuarioViewDTO;
import com.fiap.usuario.exception.UsuarioNaoEncontradoException;
import com.fiap.usuario.model.Usuario;
import com.fiap.usuario.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioViewDTO save(UsuarioCreateDTO usuarioCreateDTO){

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioCreateDTO.senha());

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCreateDTO, usuario);
        usuario.setSenha(senhaCriptografada);

        return new UsuarioViewDTO(usuarioRepository.save(usuario));
    }

    public UsuarioViewDTO findById(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()){
            return new UsuarioViewDTO(usuarioOptional.get());
        }else{
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }
    }

    public Page<UsuarioViewDTO> findAll(Pageable paginacao){

        return usuarioRepository
                .findAll(paginacao)
                .map(UsuarioViewDTO::new);

    }

    public void deleteById(Long id){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()){
            usuarioRepository.delete(usuarioOptional.get());
        }else{
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }
    }

    public UsuarioViewDTO update(UsuarioUpdateDTO usuarioUpdateDTO){

        // Obtém o email do usuário autenticado
        String userNameAuthenticated = SecurityContextHolder.getContext().getAuthentication().getName();

        // Obtém o usuário autenticado pelo userName
        Usuario usuarioAutenticado = (Usuario) usuarioRepository.findByEmail(userNameAuthenticated);

        // Verifica se o usuário autenticado está tentando alterar a si mesmo
        if (usuarioAutenticado.getUsuarioId().equals(usuarioUpdateDTO.usuarioId())) {
            throw new AccessDeniedException("Usuário não pode alterar a si mesmo");
        } else {

            String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioUpdateDTO.senha());

            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioUpdateDTO.usuarioId());

            if (usuarioOptional.isPresent()){
                String preservarEmail = usuarioOptional.get().getEmail();
                Usuario usuarioEncontrado = new Usuario();
                BeanUtils.copyProperties(usuarioUpdateDTO, usuarioEncontrado);
                //Não permite alterar o email pois é o username
                usuarioEncontrado.setEmail(preservarEmail);
                usuarioEncontrado.setSenha(senhaCriptografada);

                return new UsuarioViewDTO(usuarioRepository.save(usuarioEncontrado));
            }else{
                throw new UsuarioNaoEncontradoException("Usuário não encontrado");
            }
            
        }



    }

}