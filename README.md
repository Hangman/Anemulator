# Anemulator
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)  
A Game Boy emulator written in Java.  
Render engine: [libGDX](https://github.com/libgdx/libgdx)  
Sound engine: [TuningFork](https://github.com/Hangman/TuningFork)  
![Screenshot](https://github.com/Hangman/Anemulator/blob/master/assets/mario.png)

## Motivation
The Rust programming language is sexy as hell. The problem is, I can't speak or write it. To change that and make learning a new language not quite so boring, I wrote this emulator as a base for a port. The plan is to rewrite Anemulator in Rust, hopefully learning the language in the process.

## Particularities
Due to the special reason for Anemulator's existence (see [Motivation](https://github.com/Hangman/Anemulator#motivation)), the code has a few oddities:
* I put little emphasis on accuracy
* There is a lot of duplicate code, e.g. in the implementation of the CPU instructions, to have a good documentation base
* I didn't implement hardware I didn't feel like reproducing: serial port, cycle accurate DMA transfer, some MBCs
* Renders the TileMap and BG-Map next to the Game Boy screen
* No GUI support for loading ROMs
* GPU/PPU renders a full scanline per tick

## Test results
Taking into account that I didn't really care too much about hardware accuracy, most games run surprisingly well.  
On the other side... most Test Roms fail.
A few games I've tested:
| ROM                                        |   Result   |
| :----------------------------------------- | :--------: |
| Dr. Mario                                  | :+1:       |
| Tetris                                     | :+1:       |
| Kirby's Dream Land                         | :+1:       |
| Super Mario Land                           | :+1:       |
| Donkey Kong Land                           | :+1:       |
| Pinball Deluxe                             | :+1:       |
| F-1 Race                                   | :+1:       |
| Alleyway                                   | :x:        |
| Bionic Battler                             | :+1:       |
| Batman - The Video Game                    | :+1:       |
| Boxxle II                                  | :+1:       |
| Mega Man - Dr. Wily's Revenge              | :+1:       |
| Super Mario Land 2 - 6 Golden Coins        | :+1:       |
| Pokemon - Blue Edition                     | :+1:       |
| Super Mario Land 4                         | :+1:       |
| Legend of Zelda, The - Link's Awakening    | :+1:       |
| Jurassic Park                              | :+1:       |
| Legend of Zelda, The - Link's Awakening DX | :+1:       |

