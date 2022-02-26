// #include <bits/stdc++.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

using namespace std;

struct aluno;
struct materia;
struct listaAluno;
struct listaMateria;
struct periodo;

struct aluno
{
    int id;
    char cpf[20];
    char nome[50];
    listaMateria *listMat = NULL;
    aluno *prox = NULL;
};

struct listaAluno
{
    listaAluno *prox = NULL;
    aluno *alu = NULL;
};

struct materia
{
    int id, cred;
    char nome[50], professor[50];
    materia *prox = NULL;
    listaAluno *listAlu = NULL;
};

struct listaMateria
{
    listaMateria *prox = NULL;
    materia *mat = NULL;
};

struct periodo
{
    char ano[10];
    aluno *periodoAlu = NULL;
    materia *periodoMat = NULL;
    periodo *prox = NULL;
};

listaAluno *buscarAluMat(listaAluno *inicio, int id)
{
    if (inicio == nullptr)
        return nullptr;
    if (inicio->alu->id == id)
        return inicio;
    return buscarAluMat(inicio->prox, id); //não precisa de else, ja vai cair aqui automatico
}

listaAluno *buscarAnteriorAluMat(listaAluno *inicio, int id)
{

    //se a lista nao existir ou se quem for removido for o primeiro, retorna null
    if (inicio == nullptr or inicio->alu->id == id)
        return nullptr;
    //se for o ultimo da lista, ou o proximo for o aluno desejado, retorna o ponteiro atual
    if (inicio->prox == nullptr or inicio->prox->alu->id == id)
        return inicio;
    return buscarAnteriorAluMat(inicio->prox, id); // não precisa de else, ja vai cair aqui automatico
}

aluno *buscarAluPer(aluno *inicio, int id)
{
    if (inicio == nullptr)
        return nullptr;
    if (inicio->id == id)
        return inicio;
    return buscarAluPer(inicio->prox, id); // não precisa de else, ja vai cair aqui automatico
}

aluno *buscarAnteriorAluPer(aluno *inicio, int id)
{
    //se a lista nao existir ou se quem for removido for o primeiro, retorna null
    if (inicio == nullptr or inicio->id == id)
        return nullptr;
    //se for o ultimo da lista, ou o proximo for o aluno desejado, retorna o ponteiro atual
    if (inicio->prox == nullptr or inicio->prox->id == id)
        return inicio;
    return buscarAnteriorAluPer(inicio->prox, id); // não precisa de else, ja vai cair aqui automatico
}

listaMateria *buscarMatAlu(listaMateria *inicio, int id)
{
    if (inicio == nullptr)
        return nullptr;
    if (inicio->mat->id == id)
        return inicio;
    return buscarMatAlu(inicio->prox, id); //não precisa de else, ja vai cair aqui automatico
}

listaMateria *buscarAnteriorMatAlu(listaMateria *inicio, int id)
{
    //se a lista nao existir ou se quem for removido for o primeiro, retorna null
    if (inicio == nullptr or inicio->mat->id == id)
        return nullptr;
    //se for o ultimo da lista, ou o proximo for o aluno desejado, retorna o ponteiro atual
    if (inicio->prox == nullptr or inicio->prox->mat->id == id)
        return inicio;
    return buscarAnteriorMatAlu(inicio->prox, id); // não precisa de else, ja vai cair aqui automatico
}

materia *buscarMatPer(materia *inicio, int id)
{
    if (inicio == NULL)
        return NULL;
    if (inicio->id == id)
        return inicio;
    return buscarMatPer(inicio->prox, id); // não precisa de else, ja vai cair aqui automatico
}

materia *buscarAnteriorMatPer(materia *inicio, int id)
{
    //se a lista nao existir ou se quem for removido for o primeiro, retorna null
    if (inicio == nullptr or inicio->id == id)
        return nullptr;
    //se for o ultimo da lista, ou o proximo for o aluno desejado, retorna o ponteiro atual
    if (inicio->prox == nullptr or inicio->prox->id == id)
        return inicio;
    return buscarAnteriorMatPer(inicio->prox, id); // não precisa de else, ja vai cair aqui automatico
}

periodo *buscarPer(periodo *inicio, char aux_ano[20])
{
    if (inicio == nullptr)
        return inicio;
    if (strcmp(inicio->ano, aux_ano) == 0)
        return inicio;
    return buscarPer(inicio->prox, aux_ano);
}

periodo *buscarAnteriorPer(periodo *inicio, char aux_ano[20])
{
    if (inicio == nullptr or strcmp(inicio->ano, aux_ano) == 0)
        return nullptr;
    if (inicio->prox == nullptr)
        return inicio;
    if (strcmp(inicio->prox->ano, aux_ano) == 0)
        return inicio;
    return buscarAnteriorPer(inicio->prox, aux_ano);
}

//função melhorada pra fflush, fflush varia pra linux e windows
void clean_stdin()
{
    int c;
    do
    {
        c = getchar();
    } while (c != '\n' and c != EOF);
}

void dadosAlu(periodo *per, int id_alu)
{
    aluno *aux = buscarAluPer(per->periodoAlu, id_alu);
    if (aux == nullptr)
    {
        printf("O aluno com este ID não está cadastrado.\n");
        return;
    }

    printf("ID - Nome - CPF - Lista de Materias\n");

    printf("%.5d - ", aux->id);
    printf("%s - ", aux->nome);
    printf("%s - ", aux->cpf);
    listaMateria *aux_listaMateria;
    aux_listaMateria = aux->listMat;
    while (aux_listaMateria != nullptr)
    {
        printf("%s/", aux_listaMateria->mat->nome);
        aux_listaMateria = aux_listaMateria->prox;
    }
    printf("\n");
    return;
}

void dadosMat(periodo *per, int id_mat)
{
    materia *aux = buscarMatPer(per->periodoMat, id_mat);
    if (aux == nullptr)
    {
        printf("A materia com este ID nao esta cadastrada.\n");
        return;
    }
    printf("ID - Creditos - Nome - Professor - Lista de alunos\n");
    printf("%.4d - ", aux->id);
    printf("%d - ", aux->cred);
    printf("%s - ", aux->nome);
    printf("%s - ", aux->professor);
    listaAluno *aux_listaAluno;
    aux_listaAluno = aux->listAlu;
    while (aux_listaAluno != nullptr)
    {
        printf("%s/", aux_listaAluno->alu->nome);
        aux_listaAluno = aux_listaAluno->prox;
    }
    printf("\n");
    return;
}

int inserirAluMat(periodo *per, int id_mat)
{
    int id_alu = -1;
    listaAluno *aux_alu = (listaAluno *)malloc(sizeof(listaAluno));
    listaMateria *aux_mat = (listaMateria *)malloc(sizeof(listaMateria));
    printf("Digite o ID do aluno a ser matriculado:\n");
    scanf(" %d", &id_alu);
    if (id_alu < 0 || id_alu > 99999)
    {
        printf("ID invalido.\n");
        return 0;
    }
    aluno *a = buscarAluPer(per->periodoAlu, id_alu);
    if (a == nullptr)
    {
        printf("Esse aluno nao esta cadastrado neste periodo.\n");
        return 0;
    }
    /*  printf("Digite o ID da materia:\n");
    scanf(" %d", &id_mat); */
    materia *m = buscarMatPer(per->periodoMat, id_mat);
    if (m == nullptr)
    {
        printf("Essa materia nao esta cadastrada neste periodo.\n");
        return 0;
    }
    if (buscarAluMat(m->listAlu, id_alu) != nullptr)
    {
        printf("Ja existe aluno matriculado com este ID.\n");
        return 0;
    }
    aux_alu->prox = m->listAlu;
    m->listAlu = aux_alu;
    aux_alu->alu = a;

    aux_mat->prox = a->listMat;
    a->listMat = aux_mat;
    aux_mat->mat = m;

    return 1;
}

int inserirAluPer(periodo *&per, int id_alu)
{
    if (id_alu < 0 || id_alu > 99999)
    {
        printf("ID invalido.\n");
        return 0;
    }
    if (buscarAluPer(per->periodoAlu, id_alu) != nullptr)
    {
        printf("Ja existe aluno cadastrado com esse ID neste periodo.\n");
        return 0;
    }
    else
    {
        aluno *aux = (aluno *)malloc(sizeof(aluno));
        aux->prox = nullptr;
        aux->id = id_alu;
        printf("Digite o nome do aluno:\n ");
        scanf(" %49[^\n]s", aux->nome);
        clean_stdin();
        printf("Digite o cpf do aluno (Ex: 000.000.000-00, ou apenas numeros):\n");
        scanf(" %19[^\n]s", aux->cpf);
        clean_stdin();
        aux->prox = per->periodoAlu;
        per->periodoAlu = aux;
        aux->listMat = nullptr;
        return 1;
    }
}

int inserirMatPer(periodo *&per, int id_mat)
{
    if (id_mat < 0 || id_mat > 9999)
    {
        printf("ID invalido.\n");
        return 0;
    }
    if (buscarMatPer(per->periodoMat, id_mat) != nullptr)
    {

        printf("Ja existe materia cadastrada com esse ID neste periodo.\n");
        return 0;
    }
    else
    {
        materia *aux = (materia *)malloc(sizeof(materia));
        aux->id = id_mat;
        printf("Digite o nome do materia:\n");
        scanf(" %49[^\n]s", aux->nome);
        clean_stdin();
        printf("Digite o nome do professor:\n");
        scanf(" %49[^\n]s", aux->professor);
        clean_stdin();
        printf("Digite o numero de creditos necessarios para a materia:(valor inteiro entre 0 e 10)\n");
        scanf(" %d", &aux->cred);
        clean_stdin();
        if (aux->cred < 0 || aux->cred > 10)
        {
            printf("Valor de creditos invalido.\n");
            free(aux);
            return 0;
        }
        aux->prox = per->periodoMat;
        per->periodoMat = aux;
        aux->listAlu = nullptr;
        return 1;
    }
}

int inserirPer(periodo *&inicio, char aux_ano[10])
{
    if (buscarPer(inicio, aux_ano) != nullptr)
    {
        printf("Esse periodo ja esta cadastrado.\n");
        return 0;
    }
    else
    {
        periodo *aux = (periodo *)malloc(sizeof(periodo));
        strcpy(aux->ano, aux_ano);
        aux->prox = inicio;
        inicio = aux;
        inicio->periodoAlu = nullptr;
        inicio->periodoMat = nullptr;
        return 1;
    }
}

void listarAluPer(periodo *inicio)
{
    printf("Lista de alunos cadastrados no periodo:\n");
    aluno *aux = inicio->periodoAlu;
    printf("ID\tNome do aluno\n");
    while (aux != nullptr)
    {
        printf("%.5d\t%s\n", aux->id, aux->nome);
        aux = aux->prox;
    }
}
void listarMatPer(periodo *inicio)
{
    printf("Lista de materias cadastradas no periodo:\n");
    materia *aux = inicio->periodoMat;
    printf("ID\tNome da materia\n");
    while (aux != nullptr)
    {
        printf("%.4d\t%s\n", aux->id, aux->nome);
        aux = aux->prox;
    }
}
void listarPer(periodo *inicio)
{
    printf("Periodos cadastrados:\n");
    while (inicio != nullptr)
    {
        printf("%s\n", inicio->ano);
        inicio = inicio->prox;
    }
}

int aluPorMat(periodo *inicio, int id)
{
    materia *aux = buscarMatPer(inicio->periodoMat, id);
    listaAluno *alAtual = nullptr;
    if (aux == nullptr)
    {
        printf("Essa materia nao esta cadastrada neste periodo.\n");
        return 0;
    }
    else
    {
        printf("Alunos matriculados:\n");
        printf("ID\tNome\n");
        alAtual = aux->listAlu;
        while (alAtual != nullptr)
        {
            printf("%.5d\t%s\n", alAtual->alu->id, alAtual->alu->nome);
            alAtual = alAtual->prox;
        }
        return 1;
    }
}
void makeFileAlu(periodo *per)
{
    char url[20] = "alunos.txt";
    FILE *arq;
    arq = fopen(url, "w");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while (per != nullptr)
        {
            aluno *aux = per->periodoAlu;
            while (aux != nullptr)
            {
                fprintf(arq, "%s\n", per->ano);
                fprintf(arq, "%d\n", aux->id);
                fprintf(arq, "%s\n", aux->nome);
                fprintf(arq, "%s\n", aux->cpf);
                aux = aux->prox;
            }
            per = per->prox;
        }
        fclose(arq);
    }
}
void makeFileMat(periodo *per)
{
    char url[20] = "materias.txt";
    FILE *arq;
    arq = fopen(url, "w");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while (per != nullptr)
        {
            materia *aux = per->periodoMat;
            while (aux != nullptr)
            {
                fprintf(arq, "%s\n", per->ano);
                fprintf(arq, "%d\n", aux->id);
                fprintf(arq, "%s\n", aux->nome);
                fprintf(arq, "%s\n", aux->professor);
                fprintf(arq, "%d\n", aux->cred);
                aux = aux->prox;
            }
            per = per->prox;
        }
        fclose(arq);
    }
}
void makeFilePer(periodo *per)
{
    char url[20] = "periodos.txt";
    FILE *arq;
    arq = fopen(url, "w");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while (per != nullptr)
        {
            fprintf(arq, "%s\n", per->ano);

            per = per->prox;
        }
        fclose(arq);
    }
}
void makeFileAluMat(periodo *per)
{
    char url[20] = "alunosmaterias.txt";
    FILE *arq;
    arq = fopen(url, "w");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while (per != nullptr)
        {
            materia *mat = per->periodoMat;
            while (mat != nullptr)
            {
                listaAluno *lista = mat->listAlu;
                while (lista != nullptr)
                {
                    fprintf(arq, "%s\n", per->ano);
                    fprintf(arq, "%d\n", mat->id);
                    fprintf(arq, "%d\n", lista->alu->id);
                    lista = lista->prox;
                }
                mat = mat->prox;
            }
            per = per->prox;
        }
        fclose(arq);
    }
}

int matPorAlu(periodo *inicio, int id)
{
    aluno *aux = buscarAluPer(inicio->periodoAlu, id);
    listaMateria *matAtual = nullptr;
    if (aux == nullptr)
    {
        printf("Esse aluno nao esta cadastrado neste periodo.\n");
        return 0;
    }
    else
    {
        printf("Materias em que o aluno esta matriculado:\n");
        printf("ID\tMateria\t\tProf.\t\tCreditos Necessarios\n");
        matAtual = aux->listMat;
        while (matAtual != nullptr)
        {
            printf("%d\t%s\t\t%s\t\t%d\n", matAtual->mat->id, matAtual->mat->nome, matAtual->mat->professor, matAtual->mat->cred);
            matAtual = matAtual->prox;
        }
        return 1;
    }
}

int menuMain()
{
    int opcao;
    printf("\nEscolha a opcao:\n");
    printf("1. Listar peridos cadastrados\n");
    printf("2. Inserir periodo\n");
    printf("3. Remover periodo\n");
    printf("4. Consultar periodo\n");
    printf("5. Salvar dados da sessao\n");
    printf("0. Sair\n");

    scanf("%d", &opcao);
    return opcao;
}

int menuPer(periodo *per)
{
    int opcao;
    printf("\nVoce esta consultando o seguinte periodo: %s\n", per->ano);
    printf("Escolha a opcao:\n");
    printf("1. Listar alunos cadastrados\n");
    printf("2. Listar materias cadastradas\n");
    printf("3. Cadastrar materia\n");
    printf("4. Remover materia\n");
    printf("5. Cadastrar aluno\n");
    printf("6. Remover aluno\n");
    printf("7. Consultar dados de um aluno\n");
    printf("8. Entrar no menu de uma materia\n");
    printf("0. Sair\n");
    scanf("%d", &opcao);
    return opcao;
}

int menuPerMat(materia *mat)
{
    int opcao;
    printf("\nVoce esta consultando a seguinte materia: %s\n", mat->nome);
    printf("Escolha a opcao:\n");
    printf("1. Dados da materia\n");
    printf("2. Consultar lista de alunos\n");
    printf("3. Matricular aluno\n");
    printf("4. Remover aluno\n");
    printf("0. Sair\n");
    scanf("%d", &opcao);
    return opcao;
}

void readFileAlu(periodo *&init)
{
    char url[20] = "alunos.txt";
    FILE *arq;
    char ano[10];
    int id;
    char cpf[20];
    char nome[50];
    arq = fopen(url, "r");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while ((fscanf(arq, " %[^\n]s", ano)) != EOF)
        {
            periodo *per = buscarPer(init, ano);
            fscanf(arq, " %d", &id);
            fscanf(arq, " %[^\n]s", nome);
            fscanf(arq, " %[^\n]s", cpf);
            aluno *aux = (aluno *)malloc(sizeof(aluno));
            aux->prox = nullptr;
            aux->listMat = nullptr;
            aux->id = id;
            strcpy(aux->nome, nome);
            strcpy(aux->cpf, cpf);
            aux->prox = per->periodoAlu;
            per->periodoAlu = aux;
            aux->listMat = nullptr;
        }
        fclose(arq);
    }
}

void readFileMat(periodo *&init)
{
    char url[20] = "materias.txt";
    FILE *arq;
    char ano[10];
    int id;
    char nome[50];
    char prof[50];
    int cred;
    arq = fopen(url, "r");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while ((fscanf(arq, " %[^\n]s", ano)) != EOF)
        {
            periodo *per = buscarPer(init, ano);
            fscanf(arq, " %d", &id);
            fscanf(arq, " %[^\n]s", nome);
            fscanf(arq, " %[^\n]s", prof);
            fscanf(arq, " %d", &cred);
            materia *aux = (materia *)malloc(sizeof(materia));
            aux->prox = nullptr;
            aux->listAlu = nullptr;
            aux->id = id;
            strcpy(aux->nome, nome);
            strcpy(aux->professor, prof);
            aux->cred = cred;
            aux->prox = per->periodoMat;
            per->periodoMat = aux;
        }
        fclose(arq);
    }
}

void readFileMatricula(periodo *&init)
{
    char url[20] = "alunosmaterias.txt";
    periodo *per = nullptr;
    FILE *arq;
    char ano[10];
    int id_mat, id_alu;
    arq = fopen(url, "r");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while ((fscanf(arq, " %[^\n]s", ano)) != EOF)
        {
            per = buscarPer(init, ano);
            fscanf(arq, " %d", &id_mat);
            fscanf(arq, " %d", &id_alu);
            materia *mat = buscarMatPer(per->periodoMat, id_mat);
            aluno *alu = buscarAluPer(per->periodoAlu, id_alu);
            listaAluno *aux_alu = (listaAluno *)malloc(sizeof(listaAluno));
            listaMateria *aux_mat = (listaMateria *)malloc(sizeof(listaMateria));
            aux_alu->alu = alu;
            aux_mat->mat = mat;
            aux_alu->prox = mat->listAlu;
            aux_mat->prox = alu->listMat;
            alu->listMat = aux_mat;
            mat->listAlu = aux_alu;
        }
        fclose(arq);
    }
}

void readFilePer(periodo *&inicio)
{
    char url[20] = "periodos.txt";
    FILE *arq;
    char ano[10];
    arq = fopen(url, "r");
    if (arq == nullptr)
        printf("Erro ao abrir arquivo\n");
    else
    {
        while ((fscanf(arq, " %[^\n]s", ano)) != EOF)
        {
            inserirPer(inicio, ano);
        }
        fclose(arq);
    }
}

int removeAluMat(materia *&mat, aluno *&alu)
{
    //REMOVE ALUNO DA LISTA ALUNO MATERIA
    listaAluno *aluAnt = buscarAnteriorAluMat(mat->listAlu, alu->id), *aluAtual;

    //atual == nullptr: não existe lista ou preciso remover a head
    if (aluAnt == nullptr)
    {
        //se a head não existir, não faz nada
        if (mat->listAlu == nullptr)
        {

            printf("Aluno nao esta na materia!\n");
            return 0;
        }
        //head existe e quero remove-la, troco
        aluAtual = mat->listAlu;
        mat->listAlu = aluAtual->prox;
        // free(aluAtual);
        // return 1;
    }
    //atual->prox == nullptr, chegou ao final e não achou
    else if (aluAnt->prox == nullptr)
    {
        printf("Aluno nao esta na lista!\n");
        return 0;
    }
    //coloca o prox pra apontar para o proximo da lista
    else
    {
        aluAtual = aluAnt->prox;
        aluAnt->prox = aluAtual->prox;
        // free(aluAtual);
    }

    //REMOVE MATERIA DA LISTA MATERIA DO ALUNO
    listaMateria *matAnt = buscarAnteriorMatAlu(alu->listMat, mat->id), *matAtual;

    //atual == nullptr: não existe lista ou preciso remover a head
    if (matAnt == nullptr)
    {
        //se a head não existir, não faz nada
        if (alu->listMat == nullptr)
        {
            printf("Materia nao pertence ao aluno!\n");
            return 0;
        }
        //head existe e quero remove-la, troco
        matAtual = alu->listMat;
        alu->listMat = matAtual->prox;
        free(aluAtual);
        free(matAtual);
        return 1;
    }
    //atual->prox == nullptr, chegou ao final e não achou
    else if (matAnt->prox == nullptr)
    {
        printf("Aluno nao esta na lista!\n");
        return 0;
    }
    //coloca o prox pra apontar para o proximo da lista
    matAtual = matAnt->prox;
    matAnt->prox = matAtual->prox;
    free(matAtual);
    free(aluAtual);
    return 1;
}

int removeAluPer(periodo *&per, int id_alu)
{
    aluno *ant = buscarAnteriorAluPer(per->periodoAlu, id_alu), *atual;
    listaMateria *matAtual;

    //atual == nullptr: não existe lista ou preciso remover a head
    if (ant == nullptr)
    {
        //se a head não existir, não faz nada
        if (per->periodoAlu == nullptr)
        {
            printf("Aluno nao esta na lista!\n");
            return 0;
        }
        atual = per->periodoAlu;
        //head existe e quero remove-la, troco
        matAtual = atual->listMat;
        while (matAtual)
        {
            //agora remove aluMat já tira o aluno da materia e a materia do aluno, alterando o ponteiro matAtual->mat
            listaMateria *aux = matAtual->prox;
            removeAluMat(matAtual->mat, atual);
            matAtual = aux;
        }
        per->periodoAlu = atual->prox;
        free(atual);
        return 1;
    }
    //atual->prox == nullptr, chegou ao final e não achou
    else if (ant->prox == nullptr)
    {
        printf("Aluno nao esta na lista!\n");
        return 0;
    }
    //coloca o prox pra apontar para o proximo da lista
    atual = ant->prox;
    matAtual = atual->listMat;
    while (matAtual)
    {
        listaMateria *aux = matAtual->prox;
        removeAluMat(matAtual->mat, atual);
        matAtual = aux;
    }
    ant->prox = atual->prox;
    free(atual);
    return 1;
}

int removeMatPer(periodo *&per, int id_mat)
{
    materia *ant = buscarAnteriorMatPer(per->periodoMat, id_mat), *atual;
    listaAluno *aluAtual;

    //atual == nullptr: não existe lista ou preciso remover a head
    if (ant == nullptr)
    {
        //se a head não existir, não faz nada
        if (per->periodoMat == nullptr)
        {
            printf("Materia nao esta na lista!\n");
            return 0;
        }
        atual = per->periodoMat;
        //head existe e quero remove-la, troco
        aluAtual = atual->listAlu;
        while (aluAtual)
        {
            //agora remove aluMat já tira o aluno da materia e a materia do aluno, alterando o ponteiro aluAtual->alu
            listaAluno *aux = aluAtual->prox;
            removeAluMat(atual, aluAtual->alu);
            aluAtual = aux;
        }
        per->periodoMat = atual->prox;
        free(atual);
        return 1;
    }
    //atual->prox == nullptr, chegou ao final e não achou
    else if (ant->prox == nullptr)
    {
        printf("Materia nao esta na lista!\n");
        return 0;
    }
    //coloca o prox pra apontar para o proximo da lista
    atual = ant->prox;
    aluAtual = atual->listAlu;
    while (aluAtual)
    {
        listaAluno *aux = aluAtual->prox;
        removeAluMat(atual, aluAtual->alu);
        aluAtual = aux;
    }
    ant->prox = atual->prox;
    free(atual);
    return 1;
}

int delAlu(periodo *per)
{
    if (per->periodoAlu == nullptr)
        return 1;
    aluno *aux = per->periodoAlu->prox;
    removeAluPer(per, per->periodoAlu->id);
    per->periodoAlu = aux;
    delAlu(per);
    return 1;
}

int delMat(periodo *per)
{
    if (per->periodoMat == nullptr)
        return 1;
    materia *aux = per->periodoMat->prox;
    removeMatPer(per, per->periodoMat->id);
    per->periodoMat = aux;
    delMat(per);
    return 1;
}

int removePer(periodo *&per, char aux_ano[20])
{
    periodo *ant = buscarAnteriorPer(per, aux_ano), *atual;

    //atual == nullptr: não existe lista ou preciso remover a head
    if (ant == nullptr)
    {
        //se a head não existir, não faz nada
        if (per == nullptr)
        {
            printf("Periodo nao existe!\n");

            return 0;
        }
        atual = per;
        //head existe e quero remove-la, troco

        per = atual->prox;
        delAlu(atual);
        delMat(atual);
        free(atual);
        return 1;
    }
    //atual->prox == nullptr, chegou ao final e não achou
    else if (ant->prox == nullptr)
    {
        printf("Periodo nao existe!\n");
        return 0;
    }
    //coloca o prox pra apontar para o proximo da lista
    atual = ant->prox;
    ant->prox = atual->prox;
    delAlu(atual);
    delMat(atual);
    free(atual);
    return 1;
}

int delPer(periodo *&init)
{
    if (init == nullptr)
        return 1;
    delPer(init->prox);
    removePer(init, init->ano);
    init = nullptr;
    return 1;
}

int main()
{
    int n = 0;
    periodo *init = nullptr;
    printf("Voce deseja recuperar os dados de uma sessao anterior?\n1. Sim\n0. Nao\n");
    scanf(" %d", &n);
    if (n == 1)
    {
        readFilePer(init);
        readFileMat(init);
        readFileAlu(init);
        readFileMatricula(init);
    }
    int opcaoMain = -1;
    do
    {
        opcaoMain = menuMain();
        switch (opcaoMain)
        {
        case 1:
            listarPer(init);
            break;
        case 2:
            printf("Digite o periodo:(ex:2021-1)\n");
            char temp[10];
            scanf(" %9[^\n]s", temp);
            clean_stdin();
            inserirPer(init, temp);
            break;
        case 3:
            printf("Digite o periodo que deseja remover:(ex:2021-1)\n");
            scanf(" %9[^\n]s", temp);
            clean_stdin();
            removePer(init, temp);
            break; // remover periodo
        case 5:
            printf("Ao salvar esta sessao, os dados da sessao anterior serao substituidos. Deseja Prosseguir?\n1. Sim\n0. Nao\n");
            scanf(" %d", &n);
            if (n == 1)
            {
                makeFilePer(init);
                makeFileMat(init);
                makeFileAlu(init);
                makeFileAluMat(init);
            }
            break;
        case 4:
        {
            printf("Escolha o periodo:\n");
            scanf(" %9[^\n]s", temp);
            clean_stdin();
            periodo *aux_per = buscarPer(init, temp);
            if (aux_per == nullptr)
                printf("O periodo nao esta cadastrado\n");
            else
            {
                int opcaoPer = -1;
                do
                {
                    opcaoPer = menuPer(aux_per);
                    switch (opcaoPer)
                    {
                    case 1:
                        listarAluPer(aux_per);
                        break;
                    case 2:
                        listarMatPer(aux_per);
                        break;
                    case 3:
                        printf("Escolha o ID que deseja associar a esta materia:(numero natural de no maximo 4 digitos)\n");
                        int temp_id_mat;
                        scanf(" %d", &temp_id_mat);
                        clean_stdin();
                        inserirMatPer(aux_per, temp_id_mat);
                        break;
                    case 4:
                        printf("Insira o ID da materia que deseja remover:\n");
                        scanf(" %d", &temp_id_mat);
                        clean_stdin();
                        if (temp_id_mat < 0 || temp_id_mat > 9999)
                            printf("ID invalido.\n");
                        else
                            removeMatPer(aux_per, temp_id_mat);
                        break; //remover materia per
                    case 5:
                        printf("Escolha o ID que deseja associar a este aluno:(numero natural de no maximo 5 digitos)\n");
                        int temp_id_alu;
                        scanf(" %d", &temp_id_alu);
                        clean_stdin();
                        inserirAluPer(aux_per, temp_id_alu);
                        break;
                    case 6:
                        printf("Insira o ID do aluno que deseja remover do periodo:\n");
                        scanf(" %d", &temp_id_alu);
                        clean_stdin();
                        if (temp_id_alu < 0 || temp_id_alu > 99999)
                            printf("ID invalido.\n");
                        else
                            removeAluPer(aux_per, temp_id_alu);
                        break;
                    case 7:
                        printf("Insira o ID do aluno:\n");
                        scanf(" %d", &temp_id_alu);
                        clean_stdin();
                        if (temp_id_alu < 0 || temp_id_alu > 99999)
                            printf("ID invalido.\n");
                        else
                            dadosAlu(aux_per, temp_id_alu);
                        break;
                    case 8:
                        printf("Insira o ID da materia:\n");
                        scanf(" %d", &temp_id_mat);
                        clean_stdin();
                        if (temp_id_mat < 0 || temp_id_mat > 9999)
                            printf("ID invalido.\n");
                        else
                        {
                            materia *aux = buscarMatPer(aux_per->periodoMat, temp_id_mat);
                            if (aux == nullptr)
                                printf("A materia nao esta cadastrada\n");
                            else
                            {
                                int opcaoPerMat = -1;
                                do
                                {
                                    opcaoPerMat = menuPerMat(aux);
                                    switch (opcaoPerMat)
                                    {
                                    case 1:
                                        dadosMat(aux_per, temp_id_mat);
                                        break;
                                    case 2:
                                        aluPorMat(aux_per, temp_id_mat);
                                        break;
                                    case 3:
                                        inserirAluMat(aux_per, aux->id);
                                        break;
                                    case 4:
                                        printf("Insira o ID do aluno que deseja remover da materia:\n");
                                        scanf(" %d", &temp_id_alu);
                                        clean_stdin();
                                        listaAluno *aux_alu = buscarAluMat(aux->listAlu, temp_id_alu);
                                        if (aux_alu != nullptr)
                                            removeAluMat(aux, aux_alu->alu); // funcao recebe como aluno* como parametro
                                        else
                                            printf("Esse ID nao esta cadastrado\n"); // então preciso procurar aluno* com o id
                                        break;
                                    }
                                } while (opcaoPerMat);
                            }
                        }
                    }
                } while (opcaoPer);
            }
            break;
        }
        }
    } while (opcaoMain);
    delPer(init);
}
