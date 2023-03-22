import React from 'react';

import Rotas from './rotas';
import NavBar from '../components/navBar';
import 'bootswatch/dist/flatly/bootstrap.css'
import '../custom.css'

function App() {
  return (
    <><NavBar />
      <div className="container">
        <Rotas />
      </div>
    </>
  );
}

export default App;
