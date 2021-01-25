import { Component, OnInit, ViewChild } from '@angular/core';
import { Keyboard } from '@ionic-native/keyboard/ngx';
import { IonSlides, LoadingController, ToastController } from '@ionic/angular';
import { User } from 'src/app/interfaces/user';
import { AuthService } from 'src/app/services/auth.service';
import { UsuarioService } from 'src/app/services/usuario.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  @ViewChild(IonSlides) slides: IonSlides;

  // Variáveis globais
  public userLogin: User = {};
  public userRegister: User = {};
  public loading: any;

  constructor(
    private authService: AuthService,
    public keyboard: Keyboard,
    private loadingCtrl: LoadingController,
    private toastCtrl: ToastController,
    private usuarioService: UsuarioService
  ) { }

  ngOnInit() { }

  // Animacao dos slides
  segmentChanged(event: any) {
    if (event.detail.value === 'login') {
      this.slides.slidePrev();
    } else {
      this.slides.slideNext();
    }
  }

  // Registra usuário
  async register() {
    await this.presentLoading()

    try {
      const user = await this.authService.register(this.userRegister)
      this.usuarioService.setData(this.userRegister.nome,user.user.email,user.user.uid) // Service para acesso global dos dados
    } catch (error) {
      let message: string;
      switch(error.code) {
        case 'auth/email-already-in-use':
          message = "E-mail já cadastrado!"
          break
        case 'auth/invalid-email':
          message = "E-mail inválido!"
          break
      }
      this.presentToast(message)
    } finally {
      this.userRegister = {}
      this.loading.dismiss()
    }

  }

  // Login usuário
  async login() { 
    await this.presentLoading()

    try {
      let user = await this.authService.login(this.userLogin)
      // Service para acesso global dos dados, aqui so precisa do uid pois o usuario ja foi inserido
      // no banco ao se registrar
      this.usuarioService.setData("","",user.user.uid) 
    } catch (error) {
      let message: string;
      switch(error.code) {
        case 'auth/user-not-found':
          message = "Este usuário não existe!"
          break
        case 'auth/wrong-password':
          message = "E-mail ou senha inválida!"
          break
        case 'auth/invalid-email':
          message = "E-mail ou senha inválida!"
          break
      }
      this.presentToast(message)
    } finally {
      this.userLogin = {}
      this.loading.dismiss()
    }

  }

  // Recuperar senha
  async forgotPassword() { console.log("password") }

  // Mensagem loading
  async presentLoading() {
    this.loading = await this.loadingCtrl.create({message: 'Por favor, aguarde...'});
    return this.loading.present()
  }

  // Mensagem toast
  async presentToast(message: string) {
    const toast = await this.toastCtrl.create({
      message,
      duration: 2000
    });
    toast.present();
  }
}
