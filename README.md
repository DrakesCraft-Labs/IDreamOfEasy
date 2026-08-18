<p align="center"><img src="https://raw.githubusercontent.com/DrakesCraft-Labs/IDreamOfEasy/main/banner.svg" alt="IDreamOfEasy" width="100%"></p>

# IDreamOfEasy

> ### 🏰 ¡Únete a la Comunidad Oficial de DrakesCraft!
> 
> * 🎮 **IP del Servidor**: `play.drakescraft.net` *(Java 1.21.11 & Bedrock)*
> * 💬 **Discord Oficial**: [discord.gg/drakescraft](https://discord.gg/rR7FbfCt9Y)
> * 🌐 **Web & Guía**: [drakescraft.net](https://drakescraft.net) — 🛒 **Tienda**: [tienda.drakescraft.net](https://tienda.drakescraft.net)
> 
> *¡Juega con este addon y más de 80 expansiones optimizadas en vivo en nuestra network de supervivencia técnica!*

---

Herramientas, máquinas e ídolos para Slimefun, adaptado al ecosistema de **DrakesCraft**
(Paper/Purpur 1.21.11, Java 21).

Su autor lo describe como un addon que implementa **sugerencias de la comunidad** que resultaron
viables, más utilidades sueltas. Llegó a DrakesCraft por recomendación de dos jugadores del
servidor.

## Qué añade

**Ídolos** que actúan mientras los llevas: el Terrano y el Divino, que aprovechan el nivel de
Fortuna de tu herramienta y añaden encantamientos a lo que fabricas.

**Máquinas y herramientas** de conveniencia, más el Magnetoide, que atrae lo que sueltas.

## Qué cambiamos

Este repositorio **no es un fork**: es el código original integrado en el ecosistema de
DrakesCraft.

**Se autodesactivaba dos veces.** El fork traía un candado que apagaba el plugin si no encontraba
`GuizhanLibPlugin`, y otro que lo apagaba en versiones anteriores a 1.20.6. No usamos esa librería
—arrastra un autoactualizador que reemplaza el jar— y corremos 1.21.11, así que ambos sobran.

Aplicando una lección que nos costó cara: **quitar el candado no basta, hay que quitar también el
uso**. En otro addon dejamos la comprobación fuera pero el código seguía llamando a la librería, y
llegó a producción lanzando `NoClassDefFoundError` en cada clic de inventario. Aquí se quitaron
las dos cosas: `EnchantmentX.FORTUNE` pasa a `Enchantment.FORTUNE`, que es nativo desde el
renombrado de encantamientos.

**Un renombre que no conocíamos.** `Material.CHAIN` dejó de existir: al añadir las cadenas de
cobre en 1.21.9, la normal pasó a llamarse `IRON_CHAIN`. Está incorporado a nuestro portador para
que no vuelva a aparecer.

**Al día con 1.21.11.** Paquetes de Slimefun al core Drake —incluido el árbol legacy de
`me.mrCookieSlime`, donde el segmento `Slimefun` desaparece, y dough, que el core relocaliza a
`com.github.drakescraft_labs.slimefun4.libraries.dough`—, `paper-api` 1.21.11 y `api-version` a
`1.21`.

## Nota sobre el otro repositorio

Este addon aparece publicado en [blob.build](https://blob.build/project/IDreamOfEasy), que enlaza
a `github.com/Bunnky/IDreamOfEasy` — un repositorio que hoy devuelve 404. La adaptación se hizo
desde el código de SlimefunGuguProject, que sí está disponible.

Antes de encontrarlo llegamos a relocalizar el bytecode del jar compilado, y funcionó a nivel de
paquetes, pero se topó con que apuntaba a una versión de dough posterior a la del core:
`CustomItemStack.create` devuelve ahí `ItemStack` y aquí `CustomItemStack`, y el tipo de retorno
forma parte de la firma en la JVM. Con la fuente disponible, ese camino dejó de hacer falta.

## Instalación

Necesita Slimefun de DrakesCraft (`Slimefun4-Drake`). Se pone el jar en `plugins/` y listo.

## Crédito

El trabajo de fondo es de **Bunnky**. Licencia **GPL-3.0**, conservada sin modificar. Los detalles
están en [UPSTREAM.md](UPSTREAM.md).
