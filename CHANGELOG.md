<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# zig-support Changelog

## [0.0.1]
### Added
- Initial parser and lexer implementation
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

### Known issues
- There are still certain problems with the parser
  - `\\` Line strings sometimes aren't parsed correctly.
  - `asm volatile` sometimes aren't parsed correctly. 
