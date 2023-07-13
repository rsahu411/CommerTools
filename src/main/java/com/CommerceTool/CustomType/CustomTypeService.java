package com.CommerceTool.CustomType;

import com.CommerceTool.exceptions.InvalidActionException;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.LocalizedString;
import com.commercetools.api.models.type.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
public class CustomTypeService {


    @Autowired
    private ProjectApiRoot apiRoot;

    @Autowired
    private CustomRepository repository;


    //Create Custom-Type
    public Type createCustomType(CustomTypeDTO customTypeDTO)
    {


        TypeDraft typeDraft = TypeDraft
                .builder()
                .key(customTypeDTO.getKey())
                .name(LocalizedString.of(Locale.US, customTypeDTO.getName()))
                .description(LocalizedString.of(Locale.US,customTypeDTO.getDescription()))
                .resourceTypeIds(customTypeDTO.getResourceTypeIds())
                .fieldDefinitions(fieldDefinitions(customTypeDTO))
                .build();
        return  repository.createCustomType(typeDraft);
    }



    // Create Field Definition
    public List<FieldDefinition> fieldDefinitions(CustomTypeDTO customTypeDTO)
    {

        List<FieldDefinition> fieldDefinitions = customTypeDTO.getFieldDefinitions()
                .stream()
                .map(fieldDTO -> {
                    return  FieldDefinition
                            .builder()
                         //  .type(FieldType.enumBuilder().values(CustomFieldEnumValue.builder().build()).build())
                            .type(type(fieldDTO))
                            .name(fieldDTO.getName())
                            .label(LocalizedString.of(Locale.US,fieldDTO.getLabel()))
                            .inputHint(fieldDTO.getInputHint())
                            .required(fieldDTO.getRequired())
                            .build();
                })
                .collect(Collectors.toList());
        return fieldDefinitions;
    }




    // Type of Field Definition
    public FieldType type(CustomFieldDTO customFieldDTO)
    {
        List<CustomFieldEnumValue> values = customFieldDTO.getValues().stream()
                .map(enumValuesDTO -> {
                    return CustomFieldEnumValue.builder().label(enumValuesDTO.getLabel()).key(enumValuesDTO.getKey()).build();
                })
                .collect(Collectors.toList());

        List<CustomFieldLocalizedEnumValue> localisedValues = customFieldDTO.getValues().stream()
                .map(enumValuesDTO -> {
                    return CustomFieldLocalizedEnumValue.builder().label(LocalizedString.of(Locale.US, enumValuesDTO.getLabel())).key(enumValuesDTO.getKey()).build();
                })
                .collect(Collectors.toList());

        return switch (customFieldDTO.getType())
                {
                    case "String"-> FieldType.stringBuilder().build();
                    case "Number"-> FieldType.numberBuilder().build();
                    case "Money"-> FieldType.moneyBuilder().build();
                    case "Date"-> FieldType.dateBuilder().build();
                    case "Boolean"-> FieldType.booleanBuilder().build();
                    case "Set"-> FieldType.setBuilder().build();
                    case "LocalizedString"-> FieldType.localizedStringBuilder().build();
                    case "DateTime"-> FieldType.dateTimeBuilder().build();
                    case "Enum"-> FieldType.enumBuilder().values(values).build();
                    case "LocalizedEnum"-> FieldType.localizedEnumBuilder().values(localisedValues).build();
                    case "Time"-> FieldType.timeBuilder().build();
                    case "Reference"-> FieldType.referenceBuilder().build();
                    default -> throw new InvalidActionException(customFieldDTO.getType());
                };
    }



    // Get All Custom Object
    public TypePagedQueryResponse getAllCustomType(String limit) {

        TypePagedQueryResponse queryResponse =apiRoot
                .types()
                .get()
                .withLimit(limit)
                .executeBlocking()
                .getBody();
        return queryResponse;
    }





    // Get Custom-Type By Id
    public  Type getCustomTypeById(String id)
    {
        Type type = apiRoot
                .types()
                .withId(id)
                .get()
                .executeBlocking()
                .getBody();
        return  type;
    }




     // Update Custom-Type
    public Type updateCustomType(CustomTypeDTO customTypeDTO, String id)
    {
        List<TypeUpdateAction> typeUpdateActions = customTypeDTO.getActions()
                .stream()
                .map(actionDTO-> {
                    return switch (actionDTO.getAction())
                            {
                                case "addFieldDefinition" -> TypeUpdateAction
                                        .addFieldDefinitionBuilder()
                                        .fieldDefinition(FieldDefinition
                                                .builder()
                                                .type(type(actionDTO))
                                                .name(actionDTO.getName())
                                                .label(LocalizedString.of(Locale.US, actionDTO.getLabel()))
                                                .inputHint(actionDTO.getInputHint())
                                                .required(actionDTO.getRequired())
                                                .build())
                                        .build();
                                case "removeFieldDefinition" -> TypeUpdateAction
                                        .removeFieldDefinitionBuilder()
                                        .fieldName(actionDTO.getFieldName())
                                        .build();
                                case "addEnumValue" -> TypeUpdateAction
                                        .addEnumValueBuilder()
                                        .fieldName(actionDTO.getFieldName())
                                        .value(CustomFieldEnumValue
                                                .builder()
                                                .key(actionDTO.getKey())
                                                .label(actionDTO.getLabel())
                                                .build())
                                        .build();
                                default -> throw new InvalidActionException(actionDTO.getAction());
                            };
                })
                .collect(Collectors.toList());

        Type type = apiRoot.types().withId(id).get().executeBlocking().getBody();


        TypeUpdate typeUpdate = TypeUpdate
                .builder()
                .version(type.getVersion())
                .actions(typeUpdateActions)
                .build();

        Type updatedType = apiRoot
                .types()
                .withId(type.getId())
                .post(typeUpdate)
                .executeBlocking()
                .getBody();
        return updatedType;
    }




    // Delete Custom-Type By Id
    public Type deleteCustomType(String id,long version) {

        Type type=apiRoot
                .types()
                .withId(id)
                .delete(version)
                .executeBlocking()
                .getBody();
        return type;

    }
}
