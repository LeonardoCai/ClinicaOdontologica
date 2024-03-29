package com.example.ClinicaOdontologica.ServiceTest;


import com.example.ClinicaOdontologica.Dto.TurnoDto;
import com.example.ClinicaOdontologica.Entity.Domicilio;
import com.example.ClinicaOdontologica.Entity.Odontologo;
import com.example.ClinicaOdontologica.Entity.Paciente;
import com.example.ClinicaOdontologica.Exception.DataInvalidException;
import com.example.ClinicaOdontologica.Exception.ResourceNotFoundException;
import com.example.ClinicaOdontologica.Service.OdontologoService;
import com.example.ClinicaOdontologica.Service.PacienteService;
import com.example.ClinicaOdontologica.Service.TurnoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class TurnosTest {

    @Autowired
    TurnoService turnoService;

    @Autowired
    PacienteService pacienteService;

    @Autowired
    OdontologoService odontologoService;

    @Test
    @Order(1)
    public void guardarTurno() throws  DataInvalidException {



        // Crear un odontólogo
        Odontologo odontologo = new Odontologo("Franco", "Ribera", "6243");

        odontologoService.agregarOdontologo(odontologo);

        // Crear un domicilio para el paciente
        Domicilio domicilio = new Domicilio("Costanera", "123", "Villa Maria", "Cordoba");

        // Crear un paciente
        Paciente paciente = new Paciente("Juan", "Roca", "483892", LocalDate.of(2023, 3, 1), domicilio);

        pacienteService.agregarPaciente(paciente);



        // Crear un turno con el odontólogo, paciente y fecha correspondientes
        TurnoDto turno = new TurnoDto(LocalDate.of(2023, 4, 15),paciente.getId(),paciente.getNombre(),odontologo.getId(),odontologo.getNombre());

        // Guardar el turno en la base de datos
        TurnoDto turnoGuardado = turnoService.agregarTurno(turno);

        // Verificar que el turno se haya guardado correctamente
//
        Assertions.assertEquals(1L, turnoGuardado.getId());




}


    @Test
    @Order(2)
    public  void listarTurno() throws ResourceNotFoundException {
     Long id=1L;

        Optional<TurnoDto> turnoBuscado = turnoService.listarTurnoOptional(id);
        assertNotNull(turnoBuscado.get());

    }



    @Test
    @Order(3)
    public void listarTurnos() throws DataInvalidException, ResourceNotFoundException {


        // Crear un odontólogo
        Odontologo odontologo = new Odontologo("Frank", "Rib", "6243");

        odontologoService.agregarOdontologo(odontologo);

        // Crear un domicilio para el paciente
        Domicilio domicilio = new Domicilio("Cosac", "123", "v Maria", "Cordoba");

        // Crear un paciente
        Paciente paciente = new Paciente("ruben", "Riba", "483892", LocalDate.of(2023, 3, 1), domicilio);

        pacienteService.agregarPaciente(paciente);



        // Crear un turno con el odontólogo, paciente y fecha correspondientes
        TurnoDto turno = new TurnoDto(LocalDate.of(2023, 4, 15),paciente.getId(),paciente.getNombre(),odontologo.getId(),odontologo.getNombre());

        // Guardar el turno en la base de datos
        TurnoDto turnoGuardado = turnoService.agregarTurno(turno);


        List<TurnoDto> listaTurnos = turnoService.listarTodosTurno();
        assertEquals(2,listaTurnos.size());
    }


    @Test
    @Order(4)
    public void actualizarTurno() throws DataInvalidException, ResourceNotFoundException {

        // Crear un odontólogo
        Odontologo odontologo = new Odontologo("Frank", "Rib", "6243");
        odontologoService.agregarOdontologo(odontologo);

        // Crear un domicilio para el paciente
        Domicilio domicilio = new Domicilio("Cosac", "123", "v Maria", "Cordoba");

        // Crear un paciente
        Paciente paciente = new Paciente("ruben", "Riba", "483892", LocalDate.of(2023, 3, 1), domicilio);
        pacienteService.agregarPaciente(paciente);

        // Crear un turno con el odontólogo, paciente y fecha correspondientes
        TurnoDto turno = new TurnoDto(LocalDate.of(2023, 4, 15),paciente.getId(),paciente.getNombre(),odontologo.getId(),odontologo.getNombre());

        // Guardar el turno en la base de datos
        TurnoDto turnoGuardado = turnoService.agregarTurno(turno);

        // Actualizar el turno recién creado
        TurnoDto turnoModificado = new TurnoDto(turnoGuardado.getId(), LocalDate.of(2023, 4, 16), paciente.getId(), paciente.getNombre(), odontologo.getId(), odontologo.getNombre());
        turnoService.modificarTurno(turnoModificado);

        // Buscar el turno modificado en la base de datos
        Optional<TurnoDto> turnoBuscado = turnoService.listarTurnoOptional(turnoGuardado.getId());

        // Verificar que se haya actualizado correctamente
        Assertions.assertEquals(LocalDate.of(2023, 4, 16), turnoBuscado.get().getFecha());
    }


    @Test
    @Order(5)
    public void eliminarTurno() throws DataInvalidException, ResourceNotFoundException {
        // Crear un odontólogo
        Odontologo odontologo = new Odontologo("Frank", "Rib", "6243");
        odontologoService.agregarOdontologo(odontologo);

        // Crear un domicilio para el paciente
        Domicilio domicilio = new Domicilio("Cosac", "123", "v Maria", "Cordoba");

        // Crear un paciente
        Paciente paciente = new Paciente("ruben", "Riba", "483892", LocalDate.of(2023, 3, 1), domicilio);
        pacienteService.agregarPaciente(paciente);

        // Crear un turno con el odontólogo, paciente y fecha correspondientes
        TurnoDto turno = new TurnoDto(LocalDate.of(2023, 4, 15),paciente.getId(),paciente.getNombre(),odontologo.getId(),odontologo.getNombre());

        // Guardar el turno en la base de datos
        TurnoDto turnoGuardado = turnoService.agregarTurno(turno);

        // Eliminar el turno recién creado
        turnoService.eliminarTurno(turnoGuardado.getId());


        // Verificar que el turno ya no existe en la base de datos
       assertFalse(turnoService.listarTodosTurno().contains(turnoGuardado.getId()));
    }

}
