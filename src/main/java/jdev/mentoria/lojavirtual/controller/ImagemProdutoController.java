package jdev.mentoria.lojavirtual.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jdev.mentoria.lojavirtual.model.ImagemProduto;
import jdev.mentoria.lojavirtual.model.dto.ImagemProdutoDTO;
import jdev.mentoria.lojavirtual.repository.ImagemProdutoRepository;

@Controller
@RestController
public class ImagemProdutoController {
	
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	
	@ResponseBody
	@PostMapping(value = "**/salvarImagemProduto")
	public ResponseEntity<ImagemProdutoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) {
		
		imagemProduto = imagemProdutoRepository.saveAndFlush(imagemProduto);
		
		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
		imagemProdutoDTO.setId(imagemProduto.getId());
		imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
		imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
		imagemProdutoDTO.setImagemMinitura(imagemProduto.getImagemMinitura());
		imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
		
		return new ResponseEntity<ImagemProdutoDTO>(imagemProdutoDTO, HttpStatus.OK);

	}	
	

	@ResponseBody
	@DeleteMapping(value = "**/deleteTodaImagemProduto/{idProduto}")
	public ResponseEntity<?> deleteTodaImagemProduto(@PathVariable("idProduto") Long idProduto) {
		
		imagemProdutoRepository.deleteImagens(idProduto);
		
		return new ResponseEntity<String>("Imagens do Produto Removida", HttpStatus.OK);
	}	
		
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteImagemObjeto")
	public ResponseEntity<?> deleteImagemObjeto(@RequestBody ImagemProduto imagemProduto) {
		
		if(!imagemProdutoRepository.existsById(imagemProduto.getId())) {
			return new ResponseEntity<String>("Imagem já foi removida ou não existe com esse id: " + imagemProduto.getId(), HttpStatus.OK);	
		}
		
		imagemProdutoRepository.deleteById(imagemProduto.getId());
		
		return new ResponseEntity<String>("Imagem do Produto Removida", HttpStatus.OK);
	}	
	
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteImagemProdutoPorId/{id}")
	public ResponseEntity<?> deleteImagemProdutoPorId(@PathVariable("id") Long id) {
		
		if(!imagemProdutoRepository.existsById(id)) {
			return new ResponseEntity<String>("Imagem já foi removida ou não existe com esse id: " + id, HttpStatus.OK);	
		}		
		
		imagemProdutoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Imagem do Produto Removida", HttpStatus.OK);
	}	
	
	@ResponseBody
	@GetMapping(value = "**/obterImagemProduto/{idProduto}")
	public ResponseEntity<List<ImagemProdutoDTO>> obterImagemProduto(@PathVariable("idProduto") Long idProduto){
		
		List<ImagemProdutoDTO> dtos = new ArrayList<ImagemProdutoDTO>();
		
		List<ImagemProduto> imagemProdutos = imagemProdutoRepository.buscarImagemProduto(idProduto);
		
		for (ImagemProduto imagemProduto : imagemProdutos) {
			
			ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
			imagemProdutoDTO.setId(imagemProduto.getId());
			imagemProdutoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
			imagemProdutoDTO.setProduto(imagemProduto.getProduto().getId());
			imagemProdutoDTO.setImagemMinitura(imagemProduto.getImagemMinitura());
			imagemProdutoDTO.setImagemOriginal(imagemProduto.getImagemOriginal());
			
			dtos.add(imagemProdutoDTO);
		}
		
		return new ResponseEntity<List<ImagemProdutoDTO>>(dtos, HttpStatus.OK);
		
	}

}
