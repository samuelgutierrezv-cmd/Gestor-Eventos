Gestor de Eventos

Plataforma de gestión de eventos y venta de entradas desarrollada en Java con JavaFX. Permite a los usuarios explorar eventos, seleccionar zonas y asientos, agregar servicios adicionales y realizar compras. Los administradores pueden gestionar el catálogo completo, visualizar métricas y generar reportes operativos.

---
Integrantes

- Gina Marcela Tamayo Castañeda
- Samuel Gutierrez Vega
- Andrés Sebastián Cardona Murcia

---
📋 Descripción del proyecto

El sistema cuenta con dos perfiles principales:
Usuario: puede registrarse, explorar eventos disponibles con filtros por ciudad y categoría, seleccionar zonas y asientos, agregar servicios adicionales (VIP, parqueadero), realizar pagos simulados, consultar su historial de compras, descargar facturas y recibir notificaciones sobre cambios en sus eventos.
Administrador: puede gestionar eventos, recintos, zonas y asientos, consultar y cancelar compras, registrar incidencias, y visualizar métricas del sistema mediante gráficas de barras, torta y líneas con JavaFX Charts. También puede generar reportes exportables en CSV y PDF.

---

Instrucciones para compilar y ejecutar
Requisitos
Java 21 (JDK 21)
Maven 3.8+
JavaFX 21
Clonar el repositorio
```bash
git clone https://github.com/samuelgutierrezv-cmd/Gestor-Eventos.git
cd Gestor-Eventos
```
Compilar y ejecutar
```bash
mvn clean javafx:run
```
Credenciales de prueba
Usuario:
Correo: `samuel@gmail.com` | Contraseña: `123`
Correo: `laura@gmail.com` | Contraseña: `123`
Administrador:
Correo: `admin@gmail.com` | Contraseña: `123`

---

🧩 Patrones de diseño implementados
Patrones Creacionales
---

1. Singleton — `RepositorioAdmin`
Requisito: RF-045 — La aplicación debe mantener un único repositorio central de datos accesible desde cualquier parte del sistema.
Problema: Si múltiples instancias del repositorio existieran simultáneamente, los datos de usuarios, eventos y compras estarían desincronizados entre pantallas.
Propósito: Garantizar que exista una única instancia del repositorio de datos durante toda la ejecución de la aplicación.
Solución: `RepositorioAdmin` implementa el patrón Singleton con constructor privado y método estático `getInstance()`. La instancia se crea la primera vez que se invoca y se reutiliza en todas las llamadas posteriores.
```java
public class RepositorioAdmin {

    private static RepositorioAdmin instancia;

    private RepositorioAdmin() {
        usuarios = new ArrayList<>();
        eventos = new ArrayList<>();
        compras = new ArrayList<>();
        cargarDatosPrueba();
    }

    public static RepositorioAdmin getInstance() {
        if (instancia == null) {
            instancia = new RepositorioAdmin();
        }
        return instancia;
    }
}
```

---

2. Factory Method — `FactoryCompras`, `FactoryEventos`, `FactoryUsuarios`
Requisito: RF-034, RF-013, RF-020 — El sistema debe poder crear compras, eventos, entradas, recintos, zonas, usuarios y administradores de forma centralizada y extensible.
Problema: Instanciar objetos directamente con `new` en los controladores genera acoplamiento fuerte y dificulta cambios futuros en la construcción de objetos.
Propósito: Delegar la creación de objetos a clases especializadas, permitiendo que el código cliente trabaje con interfaces sin conocer las implementaciones concretas.
Solución: Cada factory implementa una interfaz de creación (`CreacionCompras`, `CreacionEventos`, `CreacionUsuarios`) y centraliza la instanciación de los objetos correspondientes.
```java
public class FactoryCompras implements CreacionCompras {

    @Override
    public Pasarela creacionCompra(int id, Usuario usuarioAsociado, float valor,
            Evento eventoAsociado, Date fechaCompra, EstadoCompras estado,
            ArrayList<String> conjuntoItems, ArrayList<String> serviciosAdicionales,
            ArrayList<Entrada> entradas) {
        return new Compra(id, usuarioAsociado, valor, eventoAsociado,
                fechaCompra, estado, conjuntoItems, serviciosAdicionales, entradas);
    }

    @Override
    public Pasarela creandoEntrada(int id, Zona zona, Asiento asiento,
            double precioFinal, EstadoEntrada estado) {
        return new Entrada(id, zona, asiento, precioFinal, estado);
    }
}
```

---

3. Builder — `Zona.ZonaBuilder`
Requisito: RF-028, RF-029 — Las zonas deben configurarse con múltiples atributos opcionales (nombre, capacidad, sector, precio, asientos).
Problema: El constructor de `Zona` requiere múltiples parámetros, lo que hace el código difícil de leer y propenso a errores al pasar argumentos en el orden incorrecto.
Propósito: Construir objetos complejos paso a paso de forma legible, separando la construcción de la representación.
Solución: `Zona` contiene una clase interna estática `ZonaBuilder` que acumula los parámetros y expone un método `builder()` que construye la instancia final.
```java
Zona zonaVIP = new Zona.ZonaBuilder(150000.0, 2, Sector.VIP, "VIP", 20)
        .builder();
zonaVIP.generarAsientosAutomaticos();

// Clase interna ZonaBuilder en Zona.java
public static class ZonaBuilder {
    private double precioBase;
    private int id;
    private Sector sector;
    private String nombre;
    private int capacidad;
    private ArrayList<Asiento> configuracionAsientos = new ArrayList<>();

    public ZonaBuilder(double precioBase, int id, Sector sector,
                       String nombre, int capacidad) {
        this.precioBase = precioBase;
        this.id = id;
        this.sector = sector;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public Zona builder() {
        return new Zona(this);
    }
}
```
---

Patrones Estructurales

---

4. Decorator — `DecoratorVIP`, `DecoratorParqueadero`
Requisito: RF-009 — El usuario puede agregar servicios adicionales a su compra (VIP, parqueadero) que modifican el valor total.
Problema: Añadir servicios mediante herencia requeriría una subclase por cada combinación posible de servicios, lo cual es inmanejable.
Propósito: Agregar responsabilidades a objetos de forma dinámica en tiempo de ejecución, sin modificar su clase original.
Solución: `Decorator` es una clase abstracta que implementa `CompraInterface` y envuelve otra instancia de la misma interfaz. Los decoradores concretos `DecoratorVIP` y `DecoratorParqueadero` suman su costo al valor base.
```java
public class DecoratorVIP extends Decorator {

    public DecoratorVIP(CompraInterface compraInterface) {
        super(compraInterface);
    }

    @Override
    public double definirValorTotal() {
        return compraInterface.definirValorTotal() + 100000.0;
    }

    @Override
    public ArrayList<String> definirServiciosAdiccionales() {
        ArrayList<String> servicio = compraInterface.definirServiciosAdiccionales();
        servicio.add("Servicio adicional VIP");
        return servicio;
    }
}
```
---

5. Facade — `CompraFacade`
Requisito: RF-007 — El proceso de compra involucra múltiples subsistemas: pago, generación de entradas, validación y facturación.
Problema: Los controladores tendrían que coordinar directamente con `PagoService`, `EntradaService`, `FacturaService` y la cadena de responsabilidad, generando alto acoplamiento.
Propósito: Proporcionar una interfaz simplificada que oculta la complejidad de un conjunto de subsistemas.
Solución: `CompraFacade` expone un único método `realizarCompraCompleta()` que internamente coordina todos los servicios involucrados en el proceso de compra.
```java
public class CompraFacade {

    private PagoService pagoService;
    private EntradaService entradaService;
    private FacturaService facturaService;
    private CompraService compraService;

    public CompraFacade(Strategy strategy) {
        this.compraService = new CompraService();
        this.entradaService = new EntradaService();
        this.facturaService = new FacturaService();
        this.pagoService = new PagoService(strategy);
    }

    public boolean realizarCompraCompleta(Compra compra) {
        compraService.realizarCompra(compra);
        entradaService.generarEntradas(compra);
        pagoService.procesarPago(compra);
        Handler disponibilidad = new ValidarDisponibilidad();
        Handler pago = new ValidadorPago();
        Handler factura = new GeneradorFacturaHandler();
        disponibilidad.setSiguiente(pago);
        pago.setSiguiente(factura);
        return disponibilidad.procesar(compra);
    }
}
```
---

6. Composite — `EventoComponente`
Requisito: RF-014, RF-015 — El sistema maneja una jerarquía de elementos: Recinto contiene Zonas, y las Zonas contienen Asientos.
Problema: Tratar recintos, zonas y asientos como objetos completamente independientes complica las operaciones que deben aplicarse a toda la jerarquía.
Propósito: Componer objetos en estructuras de árbol para representar jerarquías parte-todo, permitiendo tratar objetos individuales y composiciones de forma uniforme.
Solución: La interfaz `EventoComponente` define operaciones comunes (`actualizar`, `eliminarEvento`, `buscar`, `guardarComponente`) que implementan `Recinto`, `Zona` y `Asiento`, permitiendo navegar y operar sobre la jerarquía de forma uniforme.
```java
public interface EventoComponente {
    boolean actualizar(EventoComponente componente);
    boolean eliminarEvento(int id);
    EventoComponente buscar(int id);
    boolean guardarComponente(EventoComponente componente);
}

// Zona delega operaciones a sus asientos hijos
public boolean actualizar(EventoComponente componente) {
    if (componente instanceof Zona) {
        Zona z = (Zona) componente;
        if (this.id == z.id) {
            this.nombre = z.nombre;
            this.capacidad = z.capacidad;
            return true;
        }
    }
    for (Asiento a : configuracionAsientos) {
        if (a.actualizar(componente)) return true;
    }
    return false;
}
```
---
Patrones de Comportamiento

---
7. Strategy — `Strategy`, `PagoNequi`, `PagoDaviplata`, `PagoTarjeta`
Requisito: RF-007, RF-021 — El sistema debe permitir al usuario elegir entre distintos métodos de pago simulados al momento de confirmar una compra.
Problema: Usar condicionales `if/else` o `switch` para manejar los distintos métodos de pago hace el código rígido y difícil de extender con nuevos métodos.
Propósito: Definir una familia de algoritmos, encapsular cada uno y hacerlos intercambiables en tiempo de ejecución.
Solución: La interfaz `Strategy` define el método `pago(double monto)`. Cada método de pago (`PagoNequi`, `PagoDaviplata`, `PagoTarjeta`) implementa esta interfaz. El `PagoService` recibe la estrategia por constructor y la ejecuta sin conocer su implementación concreta.
```java
public interface Strategy {
    boolean pago(double monto);
}

public class PagoNequi implements Strategy {
    @Override
    public boolean pago(double monto) {
        System.out.println("Procesando pago con Nequi: $" + monto);
        return true;
    }
}

public class PagoDaviplata implements Strategy {
    @Override
    public boolean pago(double monto) {
        System.out.println("Procesando pago con Daviplata: $" + monto);
        return true;
    }
}
```
---

8. Observer — `Observer`, `Subject`, `Evento`
Requisito: RF-008, RF-017 — Los usuarios deben recibir notificaciones cuando cambia el estado de un evento o de sus compras.
Problema: Si el evento notificara directamente a cada usuario, existiría acoplamiento fuerte entre el modelo de evento y el de usuario.
Propósito: Definir una dependencia uno-a-muchos entre objetos, de modo que cuando uno cambia su estado, todos sus dependientes son notificados automáticamente.
Solución: `Subject` define la interfaz para agregar, eliminar y notificar observers. `Observer` define el método `actualizar(String mensaje)`. La clase `Evento` implementa `Subject` y notifica a todos los usuarios suscritos cuando su estado cambia.
```java
public interface Observer {
    void actualizar(String mensaje);
}

public interface Subject {
    void agregarObserver(Observer observer);
    void eliminarObserver(Observer observer);
    void notificarObservers(String mensaje);
}

// En Evento.java
public void notificarObservers(String mensaje) {
    for (Observer observer : observers) {
        observer.actualizar(mensaje);
    }
}
```
---

9. Chain of Responsibility — `Handler`, `ValidarDisponibilidad`, `ValidadorPago`, `GeneradorFacturaHandler`
Requisito: RF-007 — El proceso de compra debe validarse en múltiples etapas: disponibilidad de entradas, validación del pago y generación de factura.
Problema: Poner todas las validaciones en un solo método genera código monolítico, difícil de mantener y de extender con nuevas validaciones.
Propósito: Evitar el acoplamiento entre el emisor de una solicitud y sus receptores, dando a más de un objeto la oportunidad de manejarla mediante una cadena.
Solución: La interfaz `Handler` define `setSiguiente()` y `procesar()`. Cada manejador concreto procesa su validación y delega al siguiente en la cadena. `CompraFacade` construye y encadena los manejadores.
```java
public interface Handler {
    void setSiguiente(Handler handler);
    boolean procesar(Compra compra);
}

public class ValidarDisponibilidad extends ManejadorBaseCompra {
    @Override
    public boolean procesar(Compra compra) {
        if (compra.getEntradas() == null || compra.getEntradas().isEmpty()) {
            System.out.println("No hay entradas disponibles");
            return false;
        }
        System.out.println("Disponibilidad validada");
        return procesarSiguiente(compra);
    }
}

// Construcción de la cadena en CompraFacade
Handler disponibilidad = new ValidarDisponibilidad();
Handler pago = new ValidadorPago();
Handler factura = new GeneradorFacturaHandler();
disponibilidad.setSiguiente(pago);
pago.setSiguiente(factura);
disponibilidad.procesar(compra);
```
---

⚙️ Principios SOLID aplicados
S — Single Responsibility Principle (Principio de Responsabilidad Única)
Cada clase tiene una única razón para cambiar. Por ejemplo, `ReportesController` se encarga exclusivamente de generar y exportar reportes (CSV y PDF). La lógica de negocio de usuarios está en `Usuario.java`, la de compras en `Compra.java`, y la persistencia centralizada en `RepositorioAdmin`. Ninguna clase mezcla responsabilidades de presentación con lógica de negocio.
```java
// ReportesController solo sabe generar reportes
public class ReportesController implements Initializable {
    @FXML private void generarCSV() { ... }
    @FXML private void generarPDF() { ... }
    private String generarContenidoReporte(String tipo, LocalDate inicio, LocalDate fin, String formato) { ... }
}
```
O — Open/Closed Principle (Principio Abierto/Cerrado)
El sistema está abierto para extensión pero cerrado para modificación. El patrón Strategy permite agregar un nuevo método de pago (por ejemplo `PagoPSE`) creando una nueva clase que implemente `Strategy`, sin modificar `PagoService` ni `CompraFacade`. De igual forma, nuevos decoradores de servicios adicionales se agregan sin tocar el código existente.
```java
// Para agregar PSE solo se crea una nueva clase, sin cambiar nada existente
public class PagoPSE implements Strategy {
    @Override
    public boolean pago(double monto) {
        System.out.println("Procesando pago con PSE: $" + monto);
        return true;
    }
}
```
L — Liskov Substitution Principle (Principio de Sustitución de Liskov)
Las subclases pueden sustituir a sus clases base sin alterar el comportamiento del programa. `DecoratorVIP` y `DecoratorParqueadero` extienden `Decorator` e implementan `CompraInterface`; cualquier parte del código que reciba una `CompraInterface` funciona correctamente con cualquier decorador. De igual forma, `FactoryCompras`, `FactoryEventos` y `FactoryUsuarios` pueden usarse donde se espera su interfaz respectiva.
```java
// CompraSimple, DecoratorVIP y DecoratorParqueadero son intercambiables
CompraInterface compra = new CompraSimple(...);
compra = new DecoratorVIP(compra);        // sustituye sin romper nada
compra = new DecoratorParqueadero(compra); // sustituye sin romper nada
double total = compra.definirValorTotal(); // funciona igual con cualquiera
```
I — Interface Segregation Principle (Principio de Segregación de Interfaces)
Las interfaces son pequeñas y específicas. En lugar de una interfaz gigante, el proyecto tiene interfaces separadas: `Strategy` (solo `pago()`), `Observer` (solo `actualizar()`), `Subject` (gestión de observers), `Handler` (cadena de responsabilidad), `CompraInterface` (valor y servicios). Ninguna clase está obligada a implementar métodos que no necesita.
```java
public interface Strategy {
    boolean pago(double monto); // solo lo que necesita una estrategia de pago
}

public interface Observer {
    void actualizar(String mensaje); // solo lo que necesita un observador
}

public interface Handler {
    void setSiguiente(Handler handler);
    boolean procesar(Compra compra);
}
```
D — Dependency Inversion Principle (Principio de Inversión de Dependencias)
Los módulos de alto nivel no dependen de módulos de bajo nivel; ambos dependen de abstracciones. `CompraFacade` recibe una `Strategy` por constructor en lugar de instanciar directamente `PagoNequi` o `PagoTarjeta`. `PagoService` también trabaja con la abstracción `Strategy`. Los controladores usan las interfaces de creación (`CreacionCompras`, `CreacionEventos`) a través de las factories.
```java
// CompraFacade depende de la abstracción Strategy, no de implementaciones concretas
public class CompraFacade {
    private PagoService pagoService;

    public CompraFacade(Strategy strategy) { // inyección de dependencia
        this.pagoService = new PagoService(strategy);
        // ...
    }
}
```
---
Diagrama de clases

El diagrama de clases se encuentra en el archivo `diagrama-clases.png` en la raíz del repositorio. Cubre las entidades principales: `Usuario`, `Administrador`, `Evento`, `Recinto`, `Zona`, `Asiento`, `Compra`, `Entrada`, `Pago`, `Facturas`, `Incidencia`, y las clases de soporte de patrones (`Strategy`, `Observer`, `Decorator`, `Handler`, `EventoComponente`, `CompraFacade`).

---

Tecnologías utilizadas
- Java 21
- JavaFX 21
- Maven
- iText 5 (generación de PDF)
- JavaFX Charts (métricas y gráficas)

---
📁 Estructura del proyecto
```
src/
├── main/
│   ├── java/org/samuel/gestor_eventos/
│   │   ├── controler/       # Controladores JavaFX y repositorio
│   │   ├── modelos/         # Entidades del dominio
│   │   ├── enums/           # Enumeraciones del sistema
│   │   ├── interfaces/
│   │   │   ├── comportamiento/  # Strategy, Observer, Handler
│   │   │   ├── creacion/        # Factory Methods, Builder, Composite
│   │   │   └── estructura/      # Decorator, Facade
│   │   └── validaciones/    # Clases de validación
│   └── resources/org/samuel/gestor_eventos/
│       ├── *.fxml           # Vistas JavaFX
│       └── *.css            # Estilos
```
