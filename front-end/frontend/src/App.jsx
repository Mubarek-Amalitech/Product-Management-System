/* eslint-disable no-unused-vars */
import React, { useState } from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom"
import './index.css'; 
import Login from './login';
import Signup from './signup';
import  Homepage from './sorting-products';
const App = () => {
  const [passwordVisible, setPasswordVisible] = useState(false);
  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  return (

<BrowserRouter>
    <Routes>
   <Route exact path='/' element= { < Login/>}/>
   <Route exact path='/signup'   element= {<Signup/>} /> 
   <Route exact path='/home'   element= {<Homepage/>} /> 
    </Routes>
    
    </BrowserRouter>

  )
}

export default App;