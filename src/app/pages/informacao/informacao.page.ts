import { Component, OnInit } from '@angular/core';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-informacao',
  templateUrl: './informacao.page.html',
  styleUrls: ['./informacao.page.scss'],
})
export class InformacaoPage implements OnInit {

  constructor(private usuarioService: UsuarioService) { }

  ngOnInit() {
    console.log(this.usuarioService.getData())
  }

}
