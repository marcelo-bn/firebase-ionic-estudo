import { Component, OnInit } from '@angular/core';
import { UsuarioService } from 'src/app/services/usuario.service';
import { AuthService } from 'src/app/services/auth.service';
import { IonSlides, LoadingController, ToastController } from '@ionic/angular';

@Component({
  selector: 'app-informacao',
  templateUrl: './informacao.page.html',
  styleUrls: ['./informacao.page.scss'],
})
export class InformacaoPage implements OnInit {
  
  public loading: any;
  public usuario: any;

  constructor(
    private usuarioService: UsuarioService,
    private loadingCtrl: LoadingController,
    private authService: AuthService
  ) { this.usuario = this.usuarioService.getData(); }

  ngOnInit() {}

  // Encerra sessão
  async logout() {
    await this.presentLoading();

    try {
      await this.authService.logout();
    } catch (error) {
      console.error(error);
    } finally {
      this.loading.dismiss();
    }
  }

  // Mensagem loading
  async presentLoading() {
    this.loading = await this.loadingCtrl.create({message: 'Por favor, aguarde...'});
    return this.loading.present()
  }

}
