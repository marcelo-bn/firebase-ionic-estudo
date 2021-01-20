import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      {
        path: 'informacao',
        loadChildren: () => import('../pages/informacao/informacao.module').then(m => m.InformacaoPageModule)
      },
      {
        path: 'cadastro',
        loadChildren: () => import('../pages/cadastro/cadastro.module').then(m => m.CadastroPageModule)
      },
      {
        path: '',
        redirectTo: '/tabs/informacao',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/informacao',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TabsPageRoutingModule {}
