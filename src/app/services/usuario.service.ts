import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor() { }

  public usuario = {
    nome: "",
    email: "",
    id: ""
  }

  setData(nome, email, id) {
    this.usuario.nome = nome;
    this.usuario.email = email;
    this.usuario.id = id;
}

  getData(){
    return this.usuario;
  }

}
