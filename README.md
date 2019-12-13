# Utilities [![Github Actions](https://github.com/ewized/y4k-utilities/workflows/Build/badge.svg)](https://github.com/ewized/y4k-utilities)

- [Year4000][year4000]
- [Docs](https://ewized.github.io/y4k-utilities/)
- [Discord](https://discord.gg/ySj69qR): #year4000
- [Dockerfile](https://github.com/Year4000/Utilities/blob/master/Dockerfile): year4000/utilities:spongevanilla

The original [Utilities] created by ewized is ported over for [Year4000].
This project is a modify version used for projects made for Year4000.
Utilities was designed to be modular this allows for support on many different project levels.

## API Changes

With this project reflects the codebase on Year4000.
It our best to maintain backwards compatibility for the moment and `@Deprecated` will be removed in newer versions.
Use Utilities with caution, there is no fact that it will work the same as it does for Year4000.

## Cloning

To grab a copy of this git repo.
You will need to have [git] install on your computer;
If you are on Windows, it is recommended that you select linux like environment.

> git clone <URL> utilities

## Building

To build this project all you need to have [gradle] and [JDK].
With in gradle the project's default tasks will handle picky things.
It is recommended to use `--daemon` this will allow for faster compiling.
For even faster compiling you can select a module to build `-p core`.
This will compile every thing that it needs and nothing else.

> gradle --daemon

## Docker

If you want to spin up a quick docker instance to test the plugin, clone this repo and run the following command.
You must run this command after you have compiled the code.
If you know how Docker works we host this image on [Docker Hub](https://hub.docker.com/r/year4000/utilities/).

> docker-compose run -p 25565:25565 --rm spongevanilla

## Output

The output of each core module is generally inside `build/libs` though core modules shade other modules.
When there is more than one jar the suffix of `-all.jar` contains shaded jar, this is the one you want.

## Using

We use JitPack for the Maven repository hosting.

- **Repository**: [https://jitpack.io](https://jitpack.io/#net.year4000/utilities)
- **Core**: net.year4000.utilities:core:master-SNAPSHOT
- **Sponge**: net.year4000.utilities:sponge:master-SNAPSHOT

## License

Copyright &copy; 2019 Year4000 [www.year4000.net][year4000]. All Rights Reserved.

The source code is provided for reference only.
You may not redistribute modified versions of the source code.

[utilities]: https://github.com/ewized/utilities/
[year4000]: https://www.year4000.net/
[gradle]: http://gradle.org/gradle-download/
[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[git]: https://git-scm.com/download
