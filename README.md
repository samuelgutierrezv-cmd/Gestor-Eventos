🎟️ Gestor-Eventos

Gestor-Eventos es una plataforma diseñada para la gestión de eventos y la venta de entradas de manera eficiente. Permite a los usuarios explorar diferentes eventos, seleccionar zonas y asientos disponibles, agregar servicios adicionales y realizar compras de forma sencilla y segura.

🚀 Características
. 🔍 Exploración de eventos (conciertos, teatro, conferencias, etc.)
. 🪑 Selección de zonas y asientos disponibles
. 🎫 Compra de entradas en línea
. ⭐ Servicios adicionales (acceso VIP, seguro de cancelación, merchandising)
. 🔔 Notificaciones sobre el estado de eventos y compras
. 👤 Gestión de usuarios
. 🛠️ Panel de administración para gestión de eventos

👥 Roles del sistema
Usuario
. Explorar eventos disponibles
. Seleccionar zonas y asientos
. Comprar entradas
. Agregar servicios adicionales
. Recibir notificaciones sobre sus compras y eventos
Administrador
. Crear y gestionar eventos
. Administrar zonas y asientos
. Supervisar ventas de entradas
. Gestionar información del sistema
🏗️ Objetivo del proyecto

Desarrollar una solución digital que facilite la organización, administración y compra de entradas para eventos, mejorando la experiencia tanto para organizadores como para asistentes.

📌 Tecnologías (ejemplo)
. Java
. Programación orientada a objetos
. Patrones de diseño
. UML para modelado del sistema
. java FX

### **PENSAMIENTO COMPUTACIONAL - PROYECTO FINAL**

#### **Abstracción**

**¿Qué se solicita finalmente? (Problema)**  
Desarrollar una **plataforma de gestión de eventos y venta de entradas** mediante una aplicación de escritorio en **JavaFX**, que permita a los **usuarios finales** explorar, seleccionar y comprar entradas para eventos (conciertos, teatro, conferencias, etc.), y a los **administradores** gestionar todo el catálogo (eventos, recintos, zonas, asientos, usuarios y compras). El sistema debe manejar el ciclo completo: exploración → selección de asientos → compra → pago simulado → generación de entradas y reportes.

**¿Qué información es relevante dado el problema anterior?**  
- **Entidades principales**: Usuario, Evento, Recinto, Zona, Asiento, Compra, Entrada, Pago, Incidencia.
- Atributos clave: fechas, estados (Evento y Compra), precios, disponibilidad de asientos, métodos de pago simulados.
- Relaciones: Un Evento pertenece a un Recinto, un Recinto tiene Zonas, una Zona tiene Asientos, una Compra contiene Entradas y Servicios adicionales.

**¿Cómo se agrupa la información relevante?**  
La información se agrupa en las siguientes clases principales (según UML):

- **Usuarios y Roles**: `Usuario`, `Administrador` (herencia o rol).
- **Catálogo de Eventos**: `Evento`, `Recinto`, `Zona`, `Asiento`.
- **Proceso de Compra**: `Compra`, `Entrada`, `Pago`, `ServicioAdicional`.
- **Soporte**: `Incidencia`, clases de reportes y estrategias de pago/validación.

**¿Qué funcionalidades se solicitan finalmente?**  
**Usuario:**
- Explorar y filtrar eventos (RF-003)
- Ver detalle y seleccionar asientos (RF-004, RF-005)
- Crear/modificar/cancelar compra (RF-006)
- Realizar pago y ver comprobantes (RF-007)
- Consultar historial y descargar reportes (RF-010, RF-011)

**Administrador:**
- CRUD completo de Usuarios, Eventos, Recintos, Zonas y Asientos
- Gestionar estados y disponibilidad
- Panel de métricas y reportes (CSV/PDF)

---

#### **Descomposición**

**¿Cómo se distribuyen las funcionalidades?**

La aplicación se divide en **dos módulos principales** con sus respectivas pantallas (Scenes en JavaFX):

1. **Módulo Usuario (Cliente)**
   - Pantalla de Login / Registro
   - Pantalla principal: Exploración de eventos con filtros (`EventosView`)
   - Detalle de Evento + Selección de Zona/Asientos
   - Carrito / Resumen de Compra (`PagoView`)
   - Historial de Compras y Reportes

2. **Módulo Administrador**
   - Dashboard con métricas (Charts)
   - CRUD Eventos, Recintos, Zonas, Asientos
   - Gestión de Usuarios y Compras
   - Reportes operativos

**Estructura de paquetes sugerida (coherente con UML):**
- `modelos` → Entidades (Evento, Zona, Compra, etc.)
- `controler` → Controladores JavaFX
- `vista` → Archivos .fxml
- `dao` / `repository` → Acceso a datos (en memoria o BD)
- `servicios` → Lógica de negocio (Estrategias de pago, validaciones)
- `util` → Reportes, utilidades

**¿Qué debo hacer para probar las funcionalidades?**

- Crear **datos de prueba** en memoria al iniciar la aplicación (usuarios, eventos, recintos, zonas y asientos).
- Probar flujos completos:
  1. Usuario → Explora → Selecciona asientos → Crea compra → Paga → Ve entrada.
  2. Administrador → Cambia estado de evento → Verifica que afecta disponibilidad.
  3. Cancelación y reembolso simulado.
- Usar **assertions** o pruebas manuales exhaustivas por cada RF.
- Validar cambios de estado y actualizaciones en tiempo real (actualizar ListView/TableView).

---

#### **Reconocimiento de Patrones**

En el desarrollo de la plataforma Gestor-Eventos, se ha determinado la implementación de diversos patrones de diseño para garantizar una estructura robusta, escalable y alineada con los principios SOLID. A continuación, se justifica la elección de cada uno basándose en la lógica del sistema:

**¿Qué puedo reutilizar de la solución de otros problemas?**

- **MVC (Model-View-Controller)**: Patrón principal usado en JavaFX (Modelo = entidades, Vista = FXML, Controlador = `*Controller`).
    
✅ La arquitectura del sistema se fundamenta en el patrón MVC para lograr una separación clara de responsabilidades. Se implementa de esta manera para que la lógica de negocio (Modelo) sea totalmente independiente de la interfaz gráfica desarrollada en JavaFX (Vista). Esto permite realizar modificaciones en el diseño de las pantallas sin afectar el procesamiento de los datos, facilitando el mantenimiento y las pruebas unitarias.

- **Repository**: Para separar el acceso a datos (fácil de cambiar a base de datos real después).

✅Se define un paquete de interfaces y clases (repository) encargado exclusivamente del ciclo de vida de los datos. Esto separa la lógica de negocio de la implementación técnica del almacenamiento.

- **Strategy**: Para diferentes métodos de pago (Tarjeta, PSE, Efectivo, etc.).
  
✅Se ha seleccionado este patrón para gestionar el requisito RF-007 (Realizar pago). Dado que el sistema debe soportar múltiples métodos de pago simulados (Tarjeta, PSE, Efectivo), el patrón Strategy permite encapsular cada algoritmo de pago en clases independientes que comparten una interfaz común. De esta forma, el sistema puede intercambiar la lógica de pago en tiempo de ejecución sin alterar el flujo de la compra, cumpliendo con el principio de Abierto/Cerrado.

- **Observer**: Para actualizar disponibilidad de asientos en tiempo real.
  
✅El sistema requiere una comunicación eficiente para las Notificaciones (RF-012). Se utiliza el patrón Observer para que los usuarios (observadores) sean notificados automáticamente ante cualquier cambio de estado en un evento (sujeto), como cancelaciones o cambios de horario. Se hace de esta forma para desacoplar el emisor de la noticia de los receptores, permitiendo que múltiples usuarios reaccionen a eventos del sistema en tiempo real.

- **Factory Method**: Para crear diferentes tipos de reportes (PDF, CSV).
  
✅Para el cumplimiento del requisito de Generación de Reportes (RF-011), se emplea el patrón Factory Method. Se hace así para centralizar la creación de los distintos formatos de salida (PDF, CSV). El sistema delega la responsabilidad de instanciar el reporte específico a una clase fábrica, lo que permite que el administrador solicite un informe sin que el cliente de la aplicación necesite conocer la lógica de construcción de cada formato.

- **Singleton**: Para el servicio de autenticación y el gestor de sesiones.
  
✅Se ha implementado este patrón para el control del SessionManager o gestor de sesiones. Dado que solo debe existir una instancia global que represente al usuario autenticado en todo el ciclo de vida de la aplicación, el Singleton garantiza un punto de acceso único. Esto asegura la integridad de la información del usuario mientras navega entre los diferentes módulos (Usuario/Administrador).

- **Decorator**: Para agregar servicios adicionales a una compra (VIP, seguro, etc.).
  
✅La necesidad de ofrecer Servicios Adicionales (RF-050), como acceso VIP o seguros de cancelación, justifica el uso del patrón Decorator. Se utiliza para añadir funcionalidades u opciones a una entrada base de forma dinámica. Se ha decidido por esta aproximación para evitar una explosión de subclases innecesarias, permitiendo que una entrada estándar sea "decorada" con múltiples servicios según la elección del usuario sin modificar la clase original.

Estos patrones permiten cumplir con los **principios SOLID** solicitados en el documento:
- **SRP**: Cada clase tiene una sola responsabilidad.
- **OCP**: Fácil extender (nuevos tipos de pago o reportes).
- **DIP**: Dependemos de abstracciones (interfaces).

