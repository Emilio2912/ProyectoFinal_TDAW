let tablaAspirantes;
let aspiranteActualId = 0;

$(document).ready(function() {
    inicializarTablaAspirantes();
    cargarAspirantes();
});

function inicializarTablaAspirantes() {
    tablaAspirantes = $('#tablaAspirantes').DataTable({
        columns: [
            { title: "Nombre" },
            { title: "Correo" },
            {
                title: "Acciones",
                orderable: false,
                searchable: false,
                defaultContent: '<button class="btn btn-primary btn-sm btn-ver">Ver</button>'
            }
        ],
        responsive: true,
    });

    $('#tablaAspirantes tbody').on('click', 'button.btn-ver', function () {
        let data = tablaAspirantes.row($(this).parents('tr')).data();
        aspiranteActualId = data[2];
        mostrarDetalleAspirante(aspiranteActualId);
    });
}

function cargarAspirantes() {
    $.ajax({
        url: '/v1/api/aspirantes',
        method: 'GET',
        success: function(res) {
            if (res.estado === 1) {
                tablaAspirantes.clear();
                res.aspirantes.forEach(asp => {
                    <!-- BOTONES QUE SE USARON AQUI ESTA MENSAJE Y CONTANCIA-->
                    let botones = `
                        <div class="d-flex">
                            <button type="button" class="btn btn-rounded btn-warning me-2 btn-mensaje" data-id="${asp.idAspirante}" data-bs-toggle="modal" data-bs-target="#mensaje">Mensaje</button>
                            <button type="button" class="btn btn-rounded btn-info me-2 btn-ver" data-id="${asp.idAspirante}" data-bs-toggle="modal" data-bs-target="#informacion">Información</button>
                            <button type="button" class="btn btn-rounded btn-light me-2 btn-constancia" data-id="${asp.idAspirante}" data-bs-toggle="modal" data-bs-target="#constancia">Constancia</button>
                        </div>`;
                    tablaAspirantes.row.add([
                        asp.nombreAspirante,
                        asp.emailAspirante,
                        botones
                    ]);
                });

                tablaAspirantes.draw();
            } else {
                console.warn('No se pudieron cargar aspirantes');
            }
        },
        error: function() {
            console.error('Error cargando aspirantes');
        }
    });
}

$('#tablaAspirantes tbody').on('click', 'button.btn-ver', function () {
    let id = $(this).data('id');
    mostrarDetalleAspirante(id);
});

function mostrarDetalleAspirante(id) {
    $.ajax({
        url: '/v1/api/aspirantes/' + id,
        method: 'GET',
        success: function(res) {
            if (res.estado === 1) {
                let a = res.aspirante;
                $('#modalAspirante .modal-title').html('Aspirante: <strong>' + a.nombreAspirante + '</strong>');
                $('#nombre_aspirante').val(a.nombreAspirante);
                $('#telefono_aspirante').val(a.telefonoAspirante);
                $('#email_aspirante').val(a.emailAspirante);
                $('#carrera_aspirante').val(a.nombreCarrera);
                $('#modalAspirante').modal('show');
            } else {
                console.warn('Aspirante no encontrado');
            }
        },
        error: function() {
            console.error('Error obteniendo detalle');
        }
    });
}
