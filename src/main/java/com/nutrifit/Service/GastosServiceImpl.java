package com.nutrifit.Service;

import com.nutrifit.Clases.Gastos;
import com.nutrifit.Clases.Usuario;
import com.nutrifit.Dao.IGastos;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastosServiceImpl implements IGastos{
    
    @Autowired
    private GastosRepository gastosRepository;
    
    @Override
    public Optional<Usuario> findById(Long id) {
        return gastosRepository.findById(id);
    }

    @Override
    public List<Gastos> findGastosByUsuarioId(Long idUsuario) {
        return gastosRepository.findGastosByUsuarioId(idUsuario);
    }
}
