# Avalon

Minecraft server core for Minecraft developers.

## Description

Avalon is server core for [Mojang](https://mojang.com) worldwide popular game called [Minecraft](https://minecraft.net).
Minecraft's developers that want to create their completely custom servers are welcome to use this server core. Also,
this core doesn't include support for plugins or anything else. This server core includes some basics vanilla features.
It should be used to develop extensive servers that are completely custom-coded.

## How-To Start

If you want to run this server currently you need to clone this repository and
run [BootLoader](https://github.com/Avalon-Minecraft/Avalon/blob/main/src/main/java/com/github/avalon/BootLoader.java).

## Command List

### Editor Commands:

* If we want to fill some area we can use /pos1 and /pos2 for selection of box that we want to fill and then we can send
  /fill [Material]
* If we want to create block under us we can use /up.

### Miscellaneous Commands:

* To give ourselves item we can use /item give [Material]

### Server Commands:

* To stop server just type /stop

## TO-DO List

* Implement all types of blocks and items. Currently, there are only few blocks for testing purposes (10 blocks).
* Implement all entities and ai (pathfinding with goals) for them.
* Implement remaining packets for example Particle packets, scoreboard packets, boss bar packets, etc
* Add some sort of movement, combat, etc validation (basic anticheat)
* Implement counter packets similar to bukkit's event cancellation.
* Implement proper chunk sending currently there is only a testing version of that so if we change blocks then newly
  joined players can not see them.

## Contribution

* If you'd like to contribute, feel free to fork and make changes, then open a pull request to master branch.

## Special thanks

* Special thanks goes mostly to [Mojang](https://mojang.com) and their amazing game.
* Another special thanks goes to [Glowstone Team](https://glowstone.net/) and their idea with Flow Network solution.

## License

* This project is open-source and under [MIT License](https://github.com/Avalon-Minecraft/Avalon/blob/main/LICENSE).






