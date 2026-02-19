# DOCUMENTACIÓN DE SISTEMAS

## Arquitectura General
Todos los proyectos siguen el patrón arquitectónico **MVC (Modelo-Vista-Controlador)**, 
separando claramente la lógica de negocio, la interfaz de usuario y el control de flujo.

## Principios de Diseño
En todos los ejercicios se aplican consistentemente los siguientes principios:

- **Inversión de Dependencias (DIP):** Se utilizan interfaces para definir contratos 
  (ej. `EstudianteService`, `Command`, `State`), permitiendo que los módulos de alto 
  nivel no dependan de implementaciones concretas.
- **Composición:** Las clases complejas se construyen combinando objetos más simples 
  (ej. `MenuController` tiene una referencia a `Service` o `Vista`).
- **Inyección de Dependencias (DI):** Las dependencias (como servicios o vistas) se 
  inyectan en los controladores (generalmente por constructor), facilitando el testeo 
  y el desacoplamiento.
- **Separación de Responsabilidades:** Cada capa del sistema tiene una única razón 
  para cambiar. El modelo no conoce la vista, la vista no conoce el modelo, y el 
  controlador orquesta sin contener lógica de negocio.
- **Programación por Interfaces:** Todas las dependencias entre capas se realizan a 
  través de abstracciones, no de implementaciones concretas, permitiendo sustituir 
  implementaciones sin afectar al resto del sistema (ej. `VistaMenuJavaFX` o 
  `ConsoleVistaMenu` son intercambiables sin modificar el controlador).

## Dualidad de Vistas
Todos los ejercicios implementan **dos versiones de la vista** bajo la misma interfaz 
`VistaMenu`:
- **`VistaMenuJavaFX`:** Interfaz gráfica con animaciones, formularios y tablas.
- **`ConsoleVistaMenu`:** Interfaz de consola con validaciones equivalentes.

Esto demuestra el principio de **Inversión de Dependencias** en acción: el 
`MenuController` no sabe ni le importa qué vista está usando.

---

### Ejercicio 1: Información Estudiantes
**Patrón Específico:** Command

#### Análisis
- **Entrada (Modelo):** Datos de `Estudiante` (Edad, Sexo, Carrera, Jornada).
- **Salida (Vista):** Estadísticas calculadas (Promedios, porcentajes por carrera/género).

#### Diseño
- **MVC:** El `MenuController` gestiona la interacción. La `VistaMenu` captura opciones. 
  El modelo `Estudiante` define la estructura de datos.
- **Patrón Command:** Se implementa mediante la interfaz `Command<T>` y clases concretas 
  como `PorcentajeHombresIngenieriaCommand`, encapsulando cada solicitud de cálculo como 
  un objeto. Esto permite agregar nuevas estadísticas sin modificar el controlador 
  (principio Open/Closed).
- **DI y Composición:** El controlador recibe instancias de los comandos y del servicio 
  `EstudianteService`, delegando en ellos la lógica y permitiendo extender nuevas 
  operaciones sin modificar el controlador.

![Diagrama de Clases](./Exercise_1/Exercise_1_DC.png)

---

### Ejercicio 2: Fumigación
**Patrón Específico:** Command

#### Análisis
- **Entrada (Modelo):** Solicitudes de fumigación (`Granjero`, Tipo de fumigación, 
  Hectáreas).
- **Salida (Vista):** Valor a pagar por cada granjero, aplicando descuentos.

#### Diseño
- **MVC:** `MenuController` coordina. `VistaMenu` muestra resultados en tabla. 
  Modelo `Granjero` y `TipoFumigacion`.
- **Patrón Command:** Clases como `CalcularFumigacionCommand` implementan la interfaz 
  `Command<T>` para encapsular la lógica de negocio de cada tipo de cálculo. El resultado 
  se envuelve en un `CommandResult<T>` que transporta tanto los datos como metadatos de 
  la operación (título, éxito/error).
- **DI y Composición:** El `MenuController` compone una lista de comandos y un 
  `GranjeroService`. El servicio se inyecta, desacoplando el acceso a datos del control 
  de flujo.

![Diagrama de Clases](./Exercise_2/Exercise_2_DC.png)

---

### Ejercicio 3: Nómina Molina Corp
**Patrón Específico:** Command (Variante Response/Request)

#### Análisis
- **Entrada (Modelo):** Datos de `Empleado` (Horas trabajadas, tarifa por hora, sexo).
- **Salida (Vista):** Desprendible de nómina (Salario bruto, retenciones, neto).

#### Diseño
- **MVC:** `MenuController` maneja la lógica de presentación.
- **Patrón Command (Request/Response):** Se introduce una variante del Command con 
  separación explícita entre `EmpleadoRequestDTO` (datos de entrada) y 
  `EmpleadoResponseDTO` (datos de salida), pasando por un `EmpleadoMapper` que 
  transforma entre capas. La interfaz `ResponseCommand<T>` recibe el RequestDTO y 
  retorna un `CommandResult<T>` tipado.
- **DTO y Mapper:** Se aplica el patrón DTO para desacoplar la vista del modelo de 
  dominio. `EmpleadoMapper` centraliza las transformaciones, evitando que la vista 
  conozca la estructura interna de `Empleado`.
- **Lógica de Negocio en el Modelo:** Las reglas de cálculo (horas extra, impuestos, 
  retención) se encapsulan en setters con lógica dentro de `Empleado`, manteniendo 
  la cohesión del modelo.
- **DI y Composición:** `EmpleadoService` es inyectado en el controlador. La composición 
  permite que el controlador maneje múltiples comandos sin conocer su implementación 
  interna.

![Diagrama de Clases](./Exercise_3/Exercise_3_DC.png)

---

### Ejercicio 4: Nómina Variable
**Patrón Específico:** Command

#### Análisis
- **Entrada (Modelo):** Información de empleados con número de hijos y salario por hora.
- **Salida (Vista):** Cálculo detallado de devengado, subsidio y total a pagar.

#### Diseño
- **MVC:** Estructura clásica con `Empleado` como modelo y `VistaMenu` como interfaz.
- **Patrón Command:** `ObtenerEmpleadosCommand` implementa la interfaz `Command` para 
  abstraer la operación de recuperación y cálculo. La lista de resultados se retorna 
  tipada como `CommandResult<List<EmpleadoDTO>>`.
- **DI y Composición:** Inyección de `EmpleadoService` en los comandos y el controlador, 
  asegurando que la lógica de negocio esté separada de la presentación.

![Diagrama de Clases](./Exercise_4/Exercise_4_DC.png)

---

### Ejercicio 5: Comisiones Ventas
**Patrón Específico:** State y Composite

#### Análisis
- **Entrada (Modelo):** `Articulo` (Nombre, Precio, Cantidad), `Venta`.
- **Salida (Vista):** Comisión total calculada según reglas de precio por artículo.

#### Diseño
- **MVC:** Controlador gestiona las ventas y transiciones de estado.
- **Patrón State:** La interfaz `EstadoVenta` define el contrato para los estados 
  (ej. `EstadoConfiguracionDinamica`), permitiendo comportamiento variable según el 
  estado del sistema sin condicionales en el controlador.
- **Patrón Composite:** La interfaz `ComponentForm` permite tratar objetos individuales 
  y composiciones de manera uniforme (estructura de árbol), facilitando la construcción 
  de formularios complejos con campos anidados.
- **DI y Composición:** El controlador mantiene referencias a `EstadoVenta`, delegando 
  el comportamiento actual al estado concreto.

![Diagrama de Clases](./Exercise_5/Exercise_5_DC.png)

---

### Ejercicio 6: Elecciones Presidenciales
**Patrón Específico:** State

#### Análisis
- **Entrada (Modelo):** Votos por candidato (`Juan`, `Pedro`, `Maria`).
- **Salida (Vista):** Ganador en primera vuelta o paso a segunda vuelta con los 
  dos candidatos más votados.

#### Diseño
- **MVC:** El controlador cambia su comportamiento según la etapa de la elección.
- **Patrón State:** La interfaz `EstadoEleccion` es implementada por 
  `EstadoPrimeraVuelta`, `EstadoSegundaVuelta`, `EstadoFinalizado`, gestionando 
  las transiciones y reglas de negocio de cada fase electoral. El controlador 
  nunca usa condicionales para saber en qué etapa está; simplemente delega al 
  estado actual.
- **Patrón Command integrado:** Dentro de cada estado se usan comandos 
  (`PrimeraVueltaResponse`, `SegundaVueltaResponse`) que implementan 
  `ResponseCommand<T>`, combinando State y Command para separar la transición 
  de estado de la ejecución de la lógica electoral.
- **DI y Composición:** El controlador mantiene una referencia a `EstadoEleccion` 
  (Composición) y cambia dinámicamente la implementación concreta (Transición de 
  estado). Los comandos reciben `CandidatoService` por inyección.

![Diagrama de Clases](./Exercise_6/Exercise_6_DC.png)

---

### Ejercicio 8: Estadísticas Clientes (Peso/Altura)
**Patrón Específico:** Command

#### Análisis
- **Entrada (Modelo):** `Cliente` (Peso, altura, sexo, color ojos/cabello).
- **Salida (Vista):** Listados filtrados por criterios complejos (ej. mujeres 
  rubias de cierta altura).

#### Diseño
- **MVC:** Controlador filtra y presenta datos.
- **Patrón Command:** `ReporteHombresCommand` y `ReporteMujeresCommand` implementan 
  la interfaz `Command<T>` para encapsular consultas y filtros complejos. Cada 
  reporte es un objeto independiente, fácil de extender o reutilizar.
- **DI y Composición:** Los comandos reciben el servicio de clientes via inyección, 
  permitiendo reutilizar la lógica de filtrado.

![Diagrama de Clases](./Exercise_8/Exercise_8_DC.png)

---

### Ejercicio 9: Función Exponencial
**Patrón Específico:** Strategy

#### Análisis
- **Entrada (Modelo):** Parámetros para la función exponencial (base, exponente).
- **Salida (Vista):** Resultado del cálculo matemático con alto rendimiento.

#### Diseño
- **MVC:** Implementado en Python con `Controlador`, `VistaMenu` y `Exponencial` 
  (Modelo/Operación).
- **Patrón Strategy:** El `Controlador` recibe un objeto `operacion` (ej. `Exponencial`) 
  que cumple con el contrato implícito (Duck Typing) de tener un método `execute`. 
  Permite intercambiar algoritmos de cálculo sin modificar el controlador.
- **Vectorización con NumPy:** Para optimizar el rendimiento del cálculo exponencial, 
  se aplica vectorización mediante **NumPy**. En lugar de iterar sobre los valores 
  con bucles Python (O(n) con overhead de intérprete), las operaciones se ejecutan 
  sobre arrays completos en una sola instrucción a nivel de C, reduciendo 
  drásticamente el tiempo de cómputo. Esto es especialmente relevante cuando se 
  evalúa la función sobre rangos amplios de valores.
```python
  # Sin vectorización (lento):
  resultados = [base ** exp for exp in valores]
  
  # Con vectorización NumPy (rápido):
  resultados = np.power(base, valores)
```
- **DI y Composición:** La inyección de dependencias es explícita en el constructor: 
  `c = Controlador(VistaMenu(), Exponencial())`. El controlador está compuesto por 
  una vista y una operación intercambiable.

![Diagrama de Clases](./Exercise_9/Exercise_9_DC.png)

---

### Nota sobre Ejercicio 7
La carpeta correspondiente al Ejercicio 7 (Accidentes de tránsito) no se encuentra 
en la estructura de directorios proporcionada, por lo que no se ha incluido en este 
análisis.