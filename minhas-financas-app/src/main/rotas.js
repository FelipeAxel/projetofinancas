import React from "react";
import {Route, Switch, HashRouter}from 'react-router-dom'
import Login from '../views/login'
import Home from "../views/home";
import CadastroUsuario from '../views/cadastroUsuario'


function Rotas(){
    return (
        <HashRouter>
            <Switch>
                <Route path="/home" component={Home}/>
                <Route path="/login" component={Login}/>
                <Route path="/cadastro-usuarios" component={CadastroUsuario}/>
            </Switch>
        </HashRouter>
    )
}

export default Rotas