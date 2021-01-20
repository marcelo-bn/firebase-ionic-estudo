import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { User } from '../interfaces/user';
import * as firebase from 'firebase/app';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private afa: AngularFireAuth) { }

  // Cria conta
  register(user:User) {
    return this.afa.createUserWithEmailAndPassword(user.email, user.password);
  }

  // Entra na conta
  login(user:User) {
    return this.afa.signInWithEmailAndPassword(user.email, user.password);
  }

  logout() {}


  getAuth() {
    return this.afa;
  }


}
