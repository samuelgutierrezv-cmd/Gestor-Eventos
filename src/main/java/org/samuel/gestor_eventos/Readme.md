# 🎟️ Plataforma de Gestión de Eventos y Venta de Entradas

## 📌 Descripción del Proyecto

Este proyecto consiste en el desarrollo de una aplicación en **Java con JavaFX**, orientada a la gestión de eventos y venta de entradas.
La plataforma permite a los usuarios explorar eventos, seleccionar asientos, realizar compras y gestionar sus transacciones, mientras que los administradores pueden controlar toda la operación del sistema.

El sistema está diseñado aplicando **Programación Orientada a Objetos**, **principios SOLID** y **patrones de diseño**, con el objetivo de construir una solución modular, escalable y fácil de mantener.

---

## 🎯 Objetivo

Desarrollar una plataforma funcional que permita:

* Gestionar eventos y recintos
* Administrar usuarios
* Simular la compra de entradas
* Generar reportes
* Aplicar buenas prácticas de desarrollo

---

## 👥 Roles del Sistema

### 👤 Usuario

* Registrarse e iniciar sesión
* Editar su perfil
* Explorar eventos
* Comprar entradas
* Agregar servicios adicionales
* Consultar historial de compras
* Descargar reportes

### 🛠️ Administrador

* Gestionar usuarios
* Crear, editar y controlar eventos
* Administrar recintos, zonas y asientos
* Supervisar compras
* Registrar incidencias
* Visualizar métricas del sistema

---

## ⚙️ Funcionalidades Principales

### 🔐 Autenticación

* Registro de usuarios
* Inicio de sesión
* Gestión de perfil

### 🎤 Gestión de Eventos

* Creación, edición y eliminación de eventos
* Publicación, pausa y cancelación
* Visualización de detalles del evento

### 🏟️ Recintos y Zonas

* Gestión de recintos
* Definición de zonas (VIP, General, etc.)
* Configuración de capacidad y precios

### 💺 Asientos

* Gestión de asientos por zona
* Estados:

    * Disponible
    * Reservado
    * Vendido
    * Bloqueado
* Visualización de disponibilidad

### 🛒 Compras

* Creación y modificación de compras
* Cancelación según políticas
* Estados de compra:

    * Creada
    * Pagada
    * Confirmada
    * Cancelada
    * Reembolsada
    * Incidencia

### 💳 Pagos

* Simulación de pagos
* Generación de comprobantes

### 🎁 Servicios Adicionales

* VIP
* Seguro
* Merchandising
* Parqueadero
* Acceso preferencial

### 📊 Reportes

* Historial de compras
* Exportación a:

    * CSV
    * PDF

### 📈 Métricas (Administrador)

* Ventas por periodo
* Ocupación por zona
* Ingresos por servicios
* Tasa de cancelación
* Eventos más populares

---

## 🧱 Modelo del Sistema (Entidades)

* Usuario
* Evento
* Recinto
* Zona
* Asiento
* Compra
* Entrada
* Pago
* Incidencia

---

## 🧩 Patrones de Diseño Implementados

### 🔹 Creacionales

* Singleton
* Builder
* Prototype
* Abstract Factory

### 🔹 Estructurales

* Decorator
* Composite
* Facade

### 🔹 Comportamentales

* Strategy
* (Otros adicionales según implementación)

---

## 🧠 Principios SOLID Aplicados

* SRP (Responsabilidad única)
* OCP (Abierto/Cerrado)
* LSP (Sustitución de Liskov)
* ISP (Segregación de interfaces)
* DIP (Inversión de dependencias)

---

## 💻 Tecnologías Utilizadas

* Java
* JavaFX
* Programación Orientada a Objetos (POO)
* Git (control de versiones)

---

## 📁 Estructura del Proyecto (Ejemplo)

```
src/
 ├── model/
 ├── controller/
 ├── view/
 ├── service/
 ├── patterns/
 └── utils/
```

---

## 📊 Reportes Generados

* Ventas por periodo
* Ocupación por zona
* Ingresos por servicios adicionales
* Tasa de cancelación
* Top eventos

---

## 🚀 Ejecución del Proyecto

1. Clonar el repositorio

```
git clone <URL_DEL_REPOSITORIO>
```

2. Abrir en un IDE (IntelliJ recomendado)

3. Ejecutar la aplicación JavaFX

---

## 📌 Consideraciones

* El sistema utiliza datos simulados
* No hay integración con pasarelas de pago reales
* La disponibilidad de asientos es controlada internamente

---

## 👨‍💻 Autor(es)

* Proyecto académico - Programación II

---

## 📚 Notas Finales

Este proyecto tiene como objetivo demostrar la aplicación práctica de:

* Diseño de software
* Patrones de diseño
* Arquitectura en capas
* Desarrollo de interfaces gráficas con JavaFX
