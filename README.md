# PackServe
Very simple configurable Velocity plugin to send players a resource pack.

### Commands
PackServe has two commands:  
`/packprompt` - Manually trigger the resource pack prompt  
`/packreload` - Reload the PackServe config (requires the `packserve.reload` permission)
### Configuration
Most of the plugin's features can be best described by its config:
```hocon
// Resource pack prompt message
// MiniMessage formatting is allowed, see https://docs.advntr.dev/minimessage/format.html
prompt-message = "<green>The resource pack is strongly reccomended!"

// Whether to mark the pack as required for the client
// (Changes the "decline" button to "disconnect")
mark-as-required = true

// Whether to kick players if the resource pack fails or is refused
kick-if-failed = false
// Kick message. MiniMessage is allowed here too.
kick-message = "<red>The resource pack is required to join the server!"

source {
  // There are current two options for the resource pack source:
  //  - A local file or directory
  //  - A github repository
  // However, unless you're using packserve for the integrated webserver,
  // there's no real point to using a static file, as you could just
  // specify it in the server's server.properties
  use-github = true

  // If using a local source, this should be the path to the zip file or directory
  local-path = "~/resourcepack.zip"

  // GitHub repository of the resource pack
  // pack.mcmeta should be in the root (top level) of the repository
  repository = "https://github.com/GitHub/ExamplePack"

  // The git branch to use
  branch = "main"
}

// Whether to use the integrated http server to serve the pack
// Not generally reccommended to use, especially for large production servers
use-integrated-server = false

// Integrated server settings
server {
  port = 12345
  external-address = "http://localhost:12345/"
}

// Alternatively, if you already have a setup for serving static content,
// PackServe can output to a specified zip file on the system
static {
  output-file = "~/files/respack.zip"
  external-url = "https://example.com/files/respack.zip"
}

// Set this to true to actually enable the plugin
configured = false
```