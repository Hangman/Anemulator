# Anemulator
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)  
A Game Boy emulator written in Java.  
Render engine: [libGDX](https://github.com/libgdx/libgdx)  
Sound engine: [TuningFork](https://github.com/Hangman/TuningFork)  
![Screenshot](https://github.com/Hangman/Anemulator/blob/master/assets/mario.png)

## Motivation
The Rust programming language is damn sexy in my opinion. The problem is, I can't speak or write it. To change that and make learning a new language not quite so boring, I wrote this emulator as a base for a port. The plan is to rewrite Anemulator in Rust, hopefully learning the language in the process.

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
| ROM                                        |   Result                 |
| :----------------------------------------- | :----------------------: |
| Dr. Mario                                  | :white_check_mark:       |
| Tetris                                     | :white_check_mark:       |
| Kirby's Dream Land                         | :white_check_mark:       |
| Super Mario Land                           | :white_check_mark:       |
| Donkey Kong Land                           | :white_check_mark:       |
| Pinball Deluxe                             | :white_check_mark:       |
| F-1 Race                                   | :white_check_mark:       |
| Alleyway                                   | :x:                      |
| Bionic Battler                             | :white_check_mark:       |
| Batman - The Video Game                    | :white_check_mark:       |
| Boxxle II                                  | :white_check_mark:       |
| Mega Man - Dr. Wily's Revenge              | :white_check_mark:       |
| Super Mario Land 2 - 6 Golden Coins        | :white_check_mark:       |
| Pokemon - Blue Edition                     | :white_check_mark:       |
| Super Mario Land 4                         | :white_check_mark:       |
| Legend of Zelda, The - Link's Awakening    | :white_check_mark:       |
| Jurassic Park                              | :white_check_mark:       |
| Legend of Zelda, The - Link's Awakening DX | :white_check_mark:       |

