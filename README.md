# fulcro-app-book-pathom

Pathom BFF for our my demo fulcro web app: https://github.com/rafaeldelboni/fulcro-app-book

## Setup

Based on `config.edn.sample` file create your own `config.edn`, you will need all Api Keys listed inside the sample file for this step.
```bash
$ cp resources/config.edn.sample resources/config.edn
```

## Usage
For development with auto-reload namespaces: 
```bash
$ lein run-dev
```

For production like just run:
```bash
$ lein run
```

## License

Copyright © 2019 Rafael Delboni

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
